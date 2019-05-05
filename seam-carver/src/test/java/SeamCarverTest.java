import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {

    SeamCarver sc6x5;
    Picture picture;

    @Before
    public void setup() throws Exception {
        picture = new Picture("seam-test-files/6x5.png");
        sc6x5 = new SeamCarver(picture);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSeamCarver() {
        new SeamCarver(null);
    }

    @Test
    public void testPicture() {
        Picture picture = new Picture("seam-test-files/6x5.png");
        assertEquals(sc6x5.picture(), picture);
    }

    @Test
    public void testPictureDefensiveCopy() {
        Picture picture = new Picture("seam-test-files/6x5.png");
        SeamCarver sc6x5 = new SeamCarver(picture);
        picture.setRGB(0, 0, 0);
        assertNotEquals(sc6x5.picture(), picture);
    }

    @Test
    public void testWidth() {
        assertEquals(sc6x5.width(), 6);
    }

    @Test
    public void testWidthSmall() {
        Picture picture = new Picture("seam-test-files/8x1.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(carver.width(), 8);
    }

    @Test
    public void testWidthLarge() {
        Picture picture = new Picture("seam-test-files/HJocean.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(carver.width(), 768);
    }

    @Test
    public void testHeight() {
        assertEquals(sc6x5.height(), 5);
    }



    @Test
    public void testHeightSmall() {
        Picture picture = new Picture("seam-test-files/8x1.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(carver.height(), 1);
    }

    @Test
    public void testHeightLarge() {
        Picture picture = new Picture("seam-test-files/HJocean.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(carver.height(), 432);
    }


    @Test
    public void testEnergyTopBorder() {
        for(int i = 0; i < sc6x5.width(); i++) {
            assertEquals(1000, sc6x5.energy(i, 0), 0.001);
        }
    }

    @Test
    public void testEnergyRightBorder() {
        for(int i = 0; i < sc6x5.height(); i++) {
            assertEquals(1000, sc6x5.energy(0, 1), 0.001);
        }
    }

    @Test
    public void testEnergyBottomBorder() {
        for(int i = 0; i < sc6x5.width(); i++) {
            assertEquals(1000, sc6x5.energy(i, sc6x5.height() - 1), 0.001);
        }
    }

    @Test
    public void testEnergyLeftBorder() {
        for(int i = 0; i < sc6x5.height(); i++) {
            assertEquals(1000, sc6x5.energy(sc6x5.width() - 1, i), 0.001);
        }
    }

    @Test
    public void testEnergy6x5() {
        assertEquals(237.35, sc6x5.energy(1, 1), 0.01);
    }

    @Test
    public void testEnergyLarge() {
        Picture picture = new Picture("seam-test-files/HJocean.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(27.459, carver.energy(456, 123), 0.01);
    }

    @Test
    public void testEnergySmall() {
        Picture picture = new Picture("seam-test-files/12x10.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(229.3, carver.energy(4, 5), 0.01);
    }

    @Test
    public void testFindVerticalSeam() {
        int[] expected = {3, 4, 3, 2, 1};
        int[] actual = sc6x5.findVerticalSeam();
        for(int i = 0; i < sc6x5.height(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testFindVerticalSeamSmall() {
        Picture picture = new Picture("seam-test-files/7x3.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] expected = {2, 3, 2};
        int[] actual = carver.findVerticalSeam();
        for(int i = 0; i < carver.height(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testFindVerticalSeamLarge() {
        Picture picture = new Picture("seam-test-files/12x10.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] expected = {6, 7, 7, 6, 6, 7, 7, 7, 8, 7};
        int[] actual = carver.findVerticalSeam();
        for(int i = 0; i < carver.height(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testFindHorizontalSeam() {
        int[] expected = {1, 2, 1, 2, 1, 0};
        int[] actual = sc6x5.findHorizontalSeam();
        for(int i = 0; i < sc6x5.width(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testFindHorizontalSeamSmall() {
        Picture picture = new Picture("seam-test-files/7x3.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] expected = {0, 1, 1, 1, 1, 1, 0};
        int[] actual = carver.findHorizontalSeam();
        for(int i = 0; i < carver.width(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testFindHorizontalSeamLarge() {
        Picture picture = new Picture("seam-test-files/12x10.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] expected = {7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3};
        int[] actual = carver.findHorizontalSeam();
        for(int i = 0; i < carver.width(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testRemoveVerticalSeamCheckPixelColors() {
        Picture original = sc6x5.picture();
        int[] seam = sc6x5.findVerticalSeam();
        sc6x5.removeVerticalSeam(seam);
        assertEquals("The width should decrease by 1", 5, sc6x5.width());
        for(int i = 0; i < sc6x5.height(); i++) {
            if(seam[i] != sc6x5.width()) {
                assertEquals(original.get(seam[i] + 1, i), sc6x5.picture().get(seam[i], i));
            }
            else {
                assertEquals("If removed last column, last column should be previous pixel",
                    original.get(seam[i] - 1, i), sc6x5.picture().get(seam[i], i));
            }
        }

    }


    @Test
    public void testRemoveVerticalSeamCheckPixelColorsSmall() {
        Picture picture = new Picture("seam-test-files/7x3.png");
        SeamCarver carver = new SeamCarver(picture);

        Picture original = carver.picture();
        int[] seam = carver.findVerticalSeam();
        carver.removeVerticalSeam(seam);
        assertEquals("The width should decrease by 1", 6, carver.width());
        for(int i = 0; i < carver.height(); i++) {
            if(seam[i] != carver.width()) {
                assertEquals(original.get(seam[i] + 1, i), carver.picture().get(seam[i], i));
            }
            else {
                assertEquals("If removed last column, last column should be previous pixel",
                    original.get(seam[i] - 1, i), carver.picture().get(seam[i], i));
            }
        }

    }

    @Test
    public void testRemoveVerticalSeamCheckPixelColorsLarge() {
        Picture picture = new Picture("seam-test-files/HJocean.png");
        SeamCarver carver = new SeamCarver(picture);

        Picture original = carver.picture();
        int[] seam = carver.findVerticalSeam();
        carver.removeVerticalSeam(seam);
        assertEquals("The width should decrease by 1", 767, carver.width());
        for(int i = 0; i < carver.height(); i++) {
            if(seam[i] != carver.width()) {
                assertEquals(original.get(seam[i] + 1, i), carver.picture().get(seam[i], i));
            }
            else {
                assertEquals("If removed last column, last column should be previous pixel",
                    original.get(seam[i] - 1, i), carver.picture().get(seam[i], i));
            }
        }

    }


    @Test
    public void testRemoveHorizontalSeamCheckPixelColors() {
        Picture original = sc6x5.picture();
        int[] seam = sc6x5.findHorizontalSeam();
        sc6x5.removeHorizontalSeam(seam);
        assertEquals("The height should decrease by 1", 4, sc6x5.height());
        for(int i = 0; i < sc6x5.width(); i++) {
            if(seam[i] != sc6x5.height()) {
                assertEquals(original.get(i, seam[i] + 1), sc6x5.picture().get(i, seam[i]));
            }
            else {
                assertEquals("If removed last row, last row should be previous pixel",
                    original.get(i, seam[i] - 1), sc6x5.picture().get(i, seam[i]));
            }
        }
    }


    @Test
    public void testRemoveHorizontalSeamCheckPixelColorsSmall() {
        Picture picture = new Picture("seam-test-files/7x3.png");
        SeamCarver carver = new SeamCarver(picture);

        Picture original = carver.picture();
        int[] seam = carver.findHorizontalSeam();
        carver.removeHorizontalSeam(seam);
        assertEquals("The height should decrease by 1", 2, carver.height());
        for(int i = 0; i < carver.width(); i++) {
            if(seam[i] != carver.height()) {
                assertEquals(original.get(i, seam[i] + 1), carver.picture().get(i, seam[i]));
            }
            else {
                assertEquals("If removed last row, last row should be previous pixel",
                    original.get(i, seam[i] - 1), carver.picture().get(i, seam[i]));
            }
        }

    }

    @Test
    public void testRemoveHorizontalSeamCheckPixelColorsLarge() {
        Picture picture = new Picture("seam-test-files/HJocean.png");
        SeamCarver carver = new SeamCarver(picture);

        Picture original = carver.picture();
        int[] seam = carver.findHorizontalSeam();
        carver.removeHorizontalSeam(seam);
        assertEquals("The height should decrease by 1", 431, carver.height());
        for(int i = 0; i < carver.width(); i++) {
            if(seam[i] != carver.height()) {
                assertEquals(original.get(i, seam[i] + 1), carver.picture().get(i, seam[i]));
            }
            else {
                assertEquals("If removed last row, last row should be previous pixel",
                    original.get(i, seam[i] - 1), carver.picture().get(i, seam[i]));
            }
        }

    }

}
