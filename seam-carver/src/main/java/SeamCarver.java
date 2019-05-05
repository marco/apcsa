import edu.princeton.cs.algs4.Picture;

/**
 * A seam-carving utility, which is used to find the optimal horizontal
 * or vertical seam to remove in a `Picture`.
 *
 * @author skunkmb
 */
public class SeamCarver {
    /**
     * The cached RGB representations for the current picture.
     */
    private int[][] pictureRGBs;

    /**
     * The cached vertical seam columns for the current picture.
     */
    private int[] cachedVerticalSeam;

    /**
     * The cached horizontal seam rows for the current picture.
     */
    private int[] cachedHorizontalSeam;

    /**
     * The cached energy values for each pixel of the current picture.
     */
    private Double[][] cachedEnergies;

    /**
     * The default energy for border pixels in the picture.
     */
    private static final int BORDER_ENERGY = 1000;

    /**
     * Constructs a `SeamCarver`.
     *
     * @param picture The image to find a seam for.
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("The picture cannot be null.");
        }

        this.pictureRGBs = getInitialRGBs(picture);
        this.cachedEnergies = new Double[picture.height()][picture.width()];
    }

    /**
     * Returns a picture given a 2D array of RGB values.
     *
     * @param rgbs The 2D array of RGB values to use.
     * @return The constructed picture.
     */
    private Picture getEquivalentPicture(int[][] rgbs) {
        Picture newPicture = new Picture(rgbs[0].length, rgbs.length);

        for (int y = 0; y < rgbs.length; y++) {
            for (int x = 0; x < rgbs[y].length; x++) {
                newPicture.setRGB(x, y, rgbs[y][x]);
            }
        }

        return newPicture;
    }

    /**
     * Returns a 2D array of RGB values for a picture.
     *
     * @param picture The picture to find RGB values for.
     * @return The 2D array of RGB values.
     */
    private int[][] getInitialRGBs(Picture picture) {
        int[][] newPictureRGBs = new int[picture.height()][picture.width()];

        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                newPictureRGBs[y][x] = picture.getRGB(x, y);
            }
        }

        return newPictureRGBs;
    }

    /**
     * Returns a 1D representation of a 2D index.
     *
     * @param col The column to use.
     * @param row The row to use.
     * @return The 1D index value.
     */
    private int get1DIndex(int col, int row) {
        return row * width() + col;
    }

    /**
     * Returns either a row or a column value for a 1D index.
     *
     * @param index The 1D index to convert.
     * @param isRow Whether or not a row value should be returned.
     * @return The 1D index to convert.
     */
    private int get2DIndex(int index, boolean isRow) {
        if (isRow) {
            return index / width();
        } else {
            return index % width();
        }
    }

    /**
     * Returns the current picture for this `SeamCarver`.
     *
     * @return The picture value for the current state of the `SeamCarver`.
     */
    public Picture picture() {
        return getEquivalentPicture(pictureRGBs);
    }

    /**
     * Returns the width of the picture represented by this `SeamCarver`.
     *
     * @return The width of the picture.
     */
    public int width() {
        return pictureRGBs[0].length;
    }

    /**
     * Returns the height of the picture represented by this `SeamCarver`.
     *
     * @return The height of the picture.
     */
    public int height() {
        return pictureRGBs.length;
    }

    /**
     * Returns the energy value for a 1D index of the picture.
     *
     * @param index The 1D index to find an energy value for.
     * @return The energy value.
     */
    private double energy1D(int index) {
        if (index == width() * height()) {
            return 0;
        }

        return energy(get2DIndex(index, false), get2DIndex(index, true));
    }

    /**
     * Returns the energy for a pixel at a given location.
     *
     * @param x The column of the pixel.
     * @param y The row of the pixel.
     * @return The energy value of the pixel.
     */
    public double energy(int x, int y) {
        if (x < 0 || x >= width()) {
            throw new IllegalArgumentException("The specified column is out of bounds.");
        }

        if (y < 0 || y >= height()) {
            throw new IllegalArgumentException("The specified row is out of bounds.");
        }

        if (cachedEnergies[y][x] != null) {
            return cachedEnergies[y][x];
        }

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return BORDER_ENERGY;
        }

        int upColor = pictureRGBs[y - 1][x];
        int leftColor = pictureRGBs[y][x - 1];
        int rightColor = pictureRGBs[y][x + 1];
        int downColor = pictureRGBs[y + 1][x];

        int deltaRX = red(leftColor) - red(rightColor);
        int deltaGX = green(leftColor) - green(rightColor);
        int deltaBX = blue(leftColor) - blue(rightColor);
        int deltaRY = red(upColor) - red(downColor);
        int deltaGY = green(upColor) - green(downColor);
        int deltaBY = blue(upColor) - blue(downColor);

        double pixelEnergy = Math.sqrt(deltaRX * deltaRX + deltaGX * deltaGX + deltaBX * deltaBX + deltaRY * deltaRY + deltaGY * deltaGY + deltaBY * deltaBY);
        cachedEnergies[y][x] = pixelEnergy;
        return pixelEnergy;
    }

    /**
     * Converts an RGB color integer into its red component.
     *
     * @param color The RGB color value.
     * @return The red component.
     */
    private int red(int color) {
        return (color >> 16) & 0xFF;
    }

    /**
     * Converts an RGB color integer into its green component.
     *
     * @param color The RGB color value.
     * @return The green component.
     */
    private int green(int color) {
        return (color >> 8) & 0xFF;
    }

    /**
     * Converts an RGB color integer into its blue component.
     *
     * @param color The RGB color value.
     * @return The blue component.
     */
    private int blue(int color) {
        return (color >> 0) & 0xFF;
    }

    /**
     * Validates that a seam is a possible seam for this `SeamCarver`.
     *
     * @param seam The seam to check.
     * @param correctLength The correct length of the seam.
     */
    private void validateSeam(int[] seam, int correctLength) {
        if (seam == null) {
            throw new IllegalArgumentException("The seam cannot be null.");
        }

        if (seam.length != correctLength) {
            throw new IllegalArgumentException("The seam is not the correct length.");
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("The maximum distance between any two seam values is 1.");
            }
        }
    }

    /**
     * Returns the optimal horizontal seam for this `SeamCarver.`
     *
     * @return The horizontal seam.
     */
    public int[] findHorizontalSeam() {
        if (cachedHorizontalSeam != null) {
            return cachedHorizontalSeam;
        }

        if (height() == 1 || height() == 2) {
            return new int[width()];
        }

        if (width() == 1) {
            return new int[] { 0 };
        }

        if (width() == 2) {
            return new int[] { 0, 0 };
        }

        double[] distTo = new double[width() * height() + 1];
        int[] edgeTo = new int[width() * height() + 1];
        int outerIndex = width() * height();

        for (int i = 0; i < distTo.length; i++) {
            if (i % width() != 0 || i == distTo.length - 1) {
                distTo[i] = Double.MAX_VALUE;
            }
        }

        for (int col = 0; col < width() - 1; col++) {
            for (int row = 0; row < height(); row++) {
                int current1DIndex = get1DIndex(col, row);
                relax(current1DIndex, get1DIndex(col + 1, row), distTo, edgeTo);

                if (row < height() - 1) {
                    relax(current1DIndex, get1DIndex(col + 1, row + 1), distTo, edgeTo);
                }

                if (row > 0) {
                    relax(current1DIndex, get1DIndex(col + 1, row - 1), distTo, edgeTo);
                }
            }
        }

        for (int row = 0; row < height(); row++) {
            relax(get1DIndex(width() - 1, row), outerIndex, distTo, edgeTo);
        }


        int[] path = new int[width()];
        int currentTarget = outerIndex;

        for (int i = path.length - 1; i >= 0; i--) {
            int newIndex = edgeTo[currentTarget];
            path[i] = get2DIndex(newIndex, true);
            currentTarget = newIndex;
        }

        cachedHorizontalSeam = path;
        return path;
    }

    /**
     * "Relaxes" a pixel by updating its energy value when finding
     * a seam.
     *
     * @param from The 1D index of the starting pixel.
     * @param to The 1D index of the ending pixel.
     * @param distTo The array of current energy values.
     * @param edgeTo The array of current edges.
     */
    private void relax(int from, int to, double[] distTo, int[] edgeTo) {
        double weight = energy1D(to);

        if (distTo[to] > distTo[from] + weight) {
            distTo[to] = distTo[from] + weight;
            edgeTo[to] = from;
        }
    }

    /**
     * Returns the optimal vertical seam for this `SeamCarver.`
     *
     * @return The vertical seam.
     */
    public int[] findVerticalSeam() {
        if (cachedVerticalSeam != null) {
            return cachedVerticalSeam;
        }

        if (width() == 1 || width() == 2) {
            return new int[height()];
        }

        if (height() == 1) {
            return new int[] { 0 };
        }

        if (height() == 2) {
            return new int[] { 0, 0 };
        }

        double[] distTo = new double[width() * height() + 1];
        int[] edgeTo = new int[width() * height() + 1];
        int outerIndex = width() * height();

        for (int i = width(); i < distTo.length; i++) {
            distTo[i] = Double.MAX_VALUE;
        }

        for (int row = 0; row < height() - 1; row++) {
            for (int col = 0; col < width(); col++) {
                int current1DIndex = get1DIndex(col, row);
                relax(current1DIndex, get1DIndex(col, row + 1), distTo, edgeTo);

                if (col < width() - 1) {
                    relax(current1DIndex, get1DIndex(col + 1, row + 1), distTo, edgeTo);
                }

                if (col > 0) {
                    relax(current1DIndex, get1DIndex(col - 1, row + 1), distTo, edgeTo);
                }
            }
        }

        for (int col = 0; col < width(); col++) {
            relax(get1DIndex(col, height() - 1), outerIndex, distTo, edgeTo);
        }

        int[] path = new int[height()];
        int currentTarget = outerIndex;

        for (int i = path.length - 1; i >= 0; i--) {
            int newIndex = edgeTo[currentTarget];
            path[i] = get2DIndex(newIndex, false);
            currentTarget = newIndex;
        }

        cachedVerticalSeam = path;
        return path;
    }

    /**
     * Removes a horizontal seam from the picture represented by this
     * `SeamCarver`.
     *
     * @param seam An array of the row to remove at each column.
     */
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, width());
        int[][] newPictureRGBs = new int[height() - 1][width()];


        for (int col = 0; col < width(); col++) {
            if (seam[col] < 0 || seam[col] >= height()) {
                throw new IllegalArgumentException("Seam value " + seam[col] + " at index " + col + " is out of bounds.");
            }

            for (int row = 0; row < seam[col]; row++) {
                newPictureRGBs[row][col] = pictureRGBs[row][col];
            }

            for (int row = seam[col]; row < height() - 1; row++) {
                newPictureRGBs[row][col] = pictureRGBs[row + 1][col];
            }
        }

        pictureRGBs = newPictureRGBs;
        resetCaches();
    }

    /**
     * Removes a vertical seam from the picture represented by this
     * `SeamCarver`.
     *
     * @param seam An array of the column to remove at each row.
     */
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, height());
        int[][] newPictureRGBs = new int[height()][width() - 1];


        for (int row = 0; row < height(); row++) {
            if (seam[row] < 0 || seam[row] >= width()) {
                throw new IllegalArgumentException("Seam value " + seam[row] + " at index " + row + " is out of bounds.");
            }

            System.arraycopy(pictureRGBs[row], 0, newPictureRGBs[row], 0, seam[row]);
            System.arraycopy(pictureRGBs[row], seam[row] + 1, newPictureRGBs[row], seam[row], width() - seam[row] - 1);
        }

        pictureRGBs = newPictureRGBs;
        resetCaches();
    }

    /**
     * Resets the current cached energy and seam values.
     */
    private void resetCaches() {
        cachedEnergies = new Double[height()][width()];
        cachedVerticalSeam = null;
        cachedHorizontalSeam = null;
    }
}