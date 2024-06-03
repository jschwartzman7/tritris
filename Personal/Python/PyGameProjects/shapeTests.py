import CurveGroups
import unittest

class TestCurveMethods(unittest.TestCase):

    def setUp(self):
        self.shape1 = [(0, 0), (0, 5), (5, 5), (5, 0)]
        self.shape2 = [(-1, 3), (2, 7), (7, 6), (4, 2), (1, 1)]
        self.shape3 = [(1, 8), (3, 8), (4, -2), (3, 1), (0.5, -1)]
        self.shape1Segments = [(self.shape1[i], self.shape1[(i+1)%len(self.shape1)]) for i in range(len(self.shape1))]
        self.shape2Segments = [(self.shape2[i], self.shape2[(i+1)%len(self.shape2)]) for i in range(len(self.shape2))]
        self.shape3Segments = [(self.shape3[i], self.shape3[(i+1)%len(self.shape3)]) for i in range(len(self.shape3))]
        self.shape1Points = [self.shape1[i] for i in range(len(self.shape1))]
        self.shape2Points = [self.shape2[i] for i in range(len(self.shape2))]
        self.shape3Points = [self.shape3[i] for i in range(len(self.shape3))]



    # pointIsInside tests
    def testPointIsInside(self):
        # points inside shape 1
        shape2PointsInShape1 = [False, False, False, True, True]
        for i in range(len(self.shape2)):
            self.assertEqual(CurveGroups.pointIsInside(self.shape2[i], self.shape1), shape2PointsInShape1[i]), "shape 2 point inside shape 1 incorrect"
        
        shape3PointsInShape1 = [False, False, False, True, False]
        for i in range(len(self.shape3)):
            self.assertEqual(CurveGroups.pointIsInside(self.shape3[i], self.shape1), shape3PointsInShape1[i]), "shape 3 point inside shape 1 incorrect"

        # points inside shape 2
        shape1PointsInShape2 = [False, False, True, False]
        for i in range(len(self.shape1)):
            self.assertEqual(CurveGroups.pointIsInside(self.shape1[i], self.shape2), shape1PointsInShape2[i]), "shape 1 point inside shape 2 incorrect"
        
        shape3PointsInShape2 = [False, False, False, False, False]
        for i in range(len(self.shape3)):
            self.assertEqual(CurveGroups.pointIsInside(self.shape3[i], self.shape2), shape3PointsInShape2[i]), "shape 3 point inside shape 2 incorrect"

        # points inside shape 3
        shape1PointsInShape3 = [False, False, False, False]
        for i in range(len(self.shape1)):
            self.assertEqual(CurveGroups.pointIsInside(self.shape1[i], self.shape3), shape1PointsInShape3[i]), "shape 1 point inside shape 3 incorrect"

        shape2PointsInShape3 = [False, True, False, False, True]
        for i in range(len(self.shape2)):
            self.assertEqual(CurveGroups.pointIsInside(self.shape2[i], self.shape3), shape2PointsInShape3[i]), "shape 2 point inside shape 3 incorrect"



    # segmentsOverlap tests
    def testSegmentsOverlap(self):
       values = [[True, False, False, False, True],
                 [True, False, False, False, False],
                 [False, False, True, False, False],
                 [False, False, False, False, False]]
       for i, segment1 in enumerate(self.shape1Segments):
           for j, segment2 in enumerate(self.shape2Segments):
               result = CurveGroups.segmentsOverlap(segment1, segment2)
               if result != None:
                   self.assertTrue(values[i][j])
               else:
                   self.assertFalse(values[i][j])


    # segmentIntersectsPolygon test
    def testSegmentIntersectsPolygon(self):
         # shape 1 segments intersect
        shape1SegmentsIntersectShape2 = [True, True, True, False]
        for i, segment in enumerate(self.shape1Segments):
            print(i)
            result = CurveGroups.segmentIntersectsPolygon(segment, self.shape2)
            if result != None:
                self.assertTrue(shape1SegmentsIntersectShape2[i])
            else:
                self.assertFalse(shape1SegmentsIntersectShape2[i])
     
        shape1SegmentsIntersectShape3 = [False, True, False, True]
        for i, segment in enumerate(self.shape1Segments):
            result = CurveGroups.segmentIntersectsPolygon(segment, self.shape3)
            if result != None:
                self.assertTrue(shape1SegmentsIntersectShape3[i])
            else:
                self.assertFalse(shape1SegmentsIntersectShape3[i])

        # shape 2 segments intersect
        shape2SegmentsIntersectShape1 = [True, False, True, False, True]
        for i, segment in enumerate(self.shape2Segments):
            result = CurveGroups.segmentIntersectsPolygon(segment, self.shape1)
            if result != None:
                self.assertTrue(shape2SegmentsIntersectShape1[i])
            else:
                self.assertFalse(shape2SegmentsIntersectShape1[i])
        
        shape2SegmentsIntersectShape3 = [True, True, False, True, True]
        for i, segment in enumerate(self.shape2Segments):
            result = CurveGroups.segmentIntersectsPolygon(segment, self.shape1)
            if result != None:
                self.assertTrue(shape2SegmentsIntersectShape3[i])
            else:
                self.assertFalse(shape2SegmentsIntersectShape3[i])

        # shape 3 segments intersect
        shape3SegmentsIntersectShape1 = [False, True, True, True, True]
        for i, segment in enumerate(self.shape3Segments):
            result = CurveGroups.segmentIntersectsPolygon(segment, self.shape1)
            if result != None:
                self.assertTrue(shape3SegmentsIntersectShape1[i])
            else:
                self.assertFalse(shape3SegmentsIntersectShape1[i])
        
        shape3SegmentsIntersectShape2 = [False, True, False, False, True]
        for i, segment in enumerate(self.shape3Segments):
            result = CurveGroups.segmentIntersectsPolygon(segment, self.shape2)
            if result != None:
                self.assertTrue(shape3SegmentsIntersectShape2[i])
            else:
                self.assertFalse(shape3SegmentsIntersectShape2[i])


if __name__ == '__main__':
    unittest.main()