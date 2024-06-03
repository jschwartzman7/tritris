import pygame
import math
import pygame.freetype as freetype


toRad = math.pi/180

class Curve:

    def __init__(self, cords):
        self.cords  = cords
        self.intersections = []
        self.minX = min([p[0] for p in self.cords])
        self.maxX = max([p[0] for p in self.cords])
        self.minY = min([p[1] for p in self.cords])
        self.maxY = max([p[1] for p in self.cords])




class CurveGroup:

    # Requires 2^N time but pruning could help
    # Ex.
    #   For n = 3 shapes, A B C
    #   if A and B don't intersect and C is contained in either A or B, then C cannot intersect the other A or B
    #   A n B = 0 & wlog C c A => C n B = 0
    # Go through each piece and store the shape intersections it contains rather than each combination maybe

    def __init__(self):
        self.curves = []
        self.values = dict()
        '''self.xVelo = lambda x, y: math.cos(x + y)
        self.yVelo = lambda x, y: math.sin(x+y)'''
        self.xVelo = lambda x, y: y/10
        self.yVelo = lambda x, y: -x/10
        self.yVelo


    def __init__(self, curves):
        self.curves = curves
        self.values = dict() # (bool1, bool2, ... , booln) -> bool
        for i in range(len(self.curves)):
            self.values[i] = list()
        self.tableSize = 2**len(curves)
        self.xVelo = lambda x, y: 0
        self.yVelo = lambda x, y: 0
        self.table = dict() # table[curveIdx] = [{intersection}]
        for j, curve in enumerate(curves):
            for x in range(curve.minX, curve.maxX + 3):
                for y in range(curve.minY, curve.maxY + 3):
                    curveIndicesOverlapped = set()
                    for i, otherCurve in enumerate(curves):
                        if pointIsInside((x, y), otherCurve.cords):
                            curveIndicesOverlapped.add(i)
                    if curveIndicesOverlapped not in self.values[j] and len(curveIndicesOverlapped) > 0:
                        self.values[j].append(curveIndicesOverlapped)


    def getIntersections(self, combinationNum):
        sections = [int(combinationNum/(self.tableSize/(2**(curveIdx+1)))) % 2 != 0 for curveIdx in range(len(self.curves))]
        return sections


    def display(self):
        for index, intersections in self.values.items():
            print(index, "-->", intersections)
            print()

    def updatePositions(self):
        for curve in self.curves:

            for cord in curve.cords:
                adjustedCord0 = cord[0]-250
                adjustedCord1 = cord[1]-250
                cord[0] += self.xVelo(adjustedCord0, adjustedCord1)
                cord[1] += self.yVelo(adjustedCord0, adjustedCord1)


    def draw(self, width, height):
        pygame.init()
        screen = pygame.display.set_mode((width, height), pygame.SCALED, vsync=1)
        clock = pygame.time.Clock()
        running = True
        while running:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False

            screen.fill("black")
            self.updatePositions()
                
            for curve in self.curves:
                pygame.draw.polygon(screen, (255,255, 255), curve.cords, width = 1)
            
            pygame.display.update()
                
            clock.tick(10)


#####################################################################################################################

'''
keep track of whether right or left of direction is inside shape1 or shape2 for next move selection

def nextVertexMove = getMove(curVertex, upcomingVertex, shape2):
    if curVertex on edge of shape2:
        we have shape2PrecVertex, shape2ProcVertex 
        add curVertex to curIntersectionPolygon
        if shape2PrecVertex interior of shape1:
            add shape2PrecVertex to 
    else:
        if (curVertex, upcomingVertex) intersects





getIntersection4(poly1, poly2):
    polygonIntersections = []   #list of individually connected polygons that compose the intersection
    curIntersectionPolygon = [(shape1V1)]
    exploredShape1 = {0}
    while there is some vertex/edge of shape1 not in explored: (for each vertex index in shape1)
        nextVertexMove()
        

        if nextVertexMove != None:
            shapeInProgress.append(nextVertexMove)
            update curEdge shape
        else:
            return shapeInProgress
    return None

getMove()...

'''

def getMove(curSegment, otherPolygon, ignoreCurPos = None):
    otherPolyTemp = otherPolygon.copy()
    otherPolyTemp.append(otherPolygon[0])
    firstIntersection = None
    firstIntersectionDistance = float("inf")
    for i in range(len(otherPolygon)):
        result = segmentsOverlap(curSegment, (otherPolyTemp[i], otherPolyTemp[i+1]))
        if result != None and result != ignoreCurPos and math.hypot((curSegment[0][0]-result[0]), curSegment[0][1]-result[1]) < firstIntersectionDistance:
            firstIntersection = (result, (i+1)%len(otherPolygon))
            firstIntersectionDistance = math.hypot((curSegment[0][0]-result[0]), curSegment[0][1]-result[1])
    return firstIntersection


def getIntersection4(poly1, poly2):
    newShape = []




def pointIsInside(point, polygon): # ray casting
        tempPolygon = polygon.copy()
        tempPolygon.append(polygon[0])
        edgeOverlaps = 0
        for vtxIdx in range(len(polygon)):
            if point[0] > max(tempPolygon[vtxIdx][0], tempPolygon[vtxIdx+1][0]):
                continue
            if point[1] < min(tempPolygon[vtxIdx][1], tempPolygon[vtxIdx+1][1]) or point[1] > max(tempPolygon[vtxIdx][1], tempPolygon[vtxIdx+1][1]):
                continue
            dy = tempPolygon[vtxIdx+1][1] - tempPolygon[vtxIdx][1]
            dx = tempPolygon[vtxIdx+1][0] - tempPolygon[vtxIdx][0]
            if dy == 0 or dx == 0:
                edgeOverlaps += 1
            else:
                slope = dy/dx
                b = tempPolygon[vtxIdx][1] - slope*tempPolygon[vtxIdx][0]
                xValIntersect = (point[1] - b)/slope
                if xValIntersect >= point[0]:
                    edgeOverlaps += 1
        return edgeOverlaps % 2 != 0


def segmentIntersectsPolygon(segment, polygon, ignore = None):
    polyTemp = polygon.copy()
    polyTemp.append(polygon[0])
    firstIntersection = None
    firstIntersectionDistance = float("inf")
    for i in range(len(polygon)):
        result = segmentsOverlap(segment, (polyTemp[i], polyTemp[i+1]))
        if result != None and result != ignore and math.hypot((segment[0][0]-result[0]), segment[0][1]-result[1]) < firstIntersectionDistance:
            firstIntersection = (result, (i, (i+1)%len(polygon)))
            firstIntersectionDistance = math.hypot((segment[0][0]-result[0]), segment[0][1]-result[1])
    return firstIntersection

def segmentsOverlap(segment1, segment2):
    dy1 = segment1[1][1] - segment1[0][1]
    dx1 = segment1[1][0] - segment1[0][0]
    dy2 = segment2[1][1] - segment2[0][1]
    dx2 = segment2[1][0] - segment2[0][0]

    maxX1 = max(segment1[0][0], segment1[1][0])
    minX1 = min(segment1[0][0], segment1[1][0])
    maxY1 = max(segment1[0][1], segment1[1][1])
    minY1 = min(segment1[0][1], segment1[1][1])

    maxX2 = max(segment2[0][0], segment2[1][0])
    minX2 = min(segment2[0][0], segment2[1][0])
    maxY2 = max(segment2[0][1], segment2[1][1])
    minY2 = min(segment2[0][1], segment2[1][1])

    if dx1 == 0 and dx2 == 0:
        return None
    elif dx1 == 0:
        if (segment1[0][0] < minX2 or segment1[0][0] > maxX2):
            return None
        yValueOfIntersect = (dy2/dx2)*segment1[0][0] + (segment2[0][1] - (dy2/dx2)*segment2[0][0])
        if (yValueOfIntersect < minY1 or yValueOfIntersect > maxY1) or (yValueOfIntersect < minY2 or yValueOfIntersect > maxY2):
            return None
        return (segment1[0][0], yValueOfIntersect)
    elif dx2 == 0:
        if (segment2[0][0] < minX1 or segment2[0][0] > maxX1):
            return None
        yValueOfIntersect = (dy1/dx1)*segment2[0][0] + (segment1[0][1] - (dy1/dx1)*segment1[0][0])
        if (yValueOfIntersect < minY1 or yValueOfIntersect > maxY1) or (yValueOfIntersect < minY2 or yValueOfIntersect > maxY2):
            return None
        return (segment2[0][0], yValueOfIntersect)
    
    m1 = dy1/dx1
    b1 = segment1[0][1] - m1*segment1[0][0]

    m2 = dy2/dx2
    b2 = segment2[0][1] - m2*segment2[0][0]
    if m1 == m2:
        return None
    xValueOfIntersect = (b2-b1)/(m1-m2)
    if (xValueOfIntersect <= minX1 or xValueOfIntersect >= maxX1) or (xValueOfIntersect <= minX2 or xValueOfIntersect >= maxX2):
        return None
    yValueOfIntersect = m1*xValueOfIntersect + b1
    if (yValueOfIntersect <= minY1 or yValueOfIntersect >= maxY1) or (yValueOfIntersect <= minY2 or yValueOfIntersect >= maxY2):
        return None
    
    return (xValueOfIntersect, yValueOfIntersect)




def getFirstIntersection(poly1, poly2):
    poly1Temp = poly1.copy()
    poly2Temp = poly2.copy()
    poly1Temp.append(poly1[0])
    poly2Temp.append(poly2[0])
    for i in range(len(poly1)):
        for j in range(len(poly2)):
            pointOfIntersection = segmentsOverlap((poly1Temp[i], poly1Temp[i+1]), (poly2Temp[j], poly2Temp[j+1]))
            if pointOfIntersection != None:
                curShape = poly2 if pointIsInside(poly1[i], poly2) else poly1
                nextHypVertexIdx = (j+1)%len(poly2) if curShape == poly2 else (i+1)%len(poly1)
                return pointOfIntersection, curShape, nextHypVertexIdx
    return None
    

'''
firstIntersection = (x, y)
curShape = shape that intersection path will lie on
nextCurShapeVtxIdx = i

newShape = [firstIntersection]
intersectionPointsFound = {(x, y)}
nextHypVertex = curShape[nextCurShapeVtxIdx]
While newShape.count(firstIntersection] == 1:
	result = (newShape[-1], nextHypVertex) overlaps otherShape:
	if result == None:
		nextCurShapeVtxIdx += 1
	else if result == (k, h), newCurShapeNextVtxIdx
		curShape = otherShape
		nextCurShapeIdx = newCurShapeNextVtxIdx
		nextHypVertex = (k, h)
	newShape.append(nextHypVertex)
	intersectionPointsFound.add(nextHypVertex)
	nextHypVertex = curShape[nextCurShapeVtxIdx]

'''
def bruh(shape):
    for cord in shape:
        if shape.count(cord) > 1:
            return False
    return True

def getIntersection3(poly1, poly2):
    intersectionResult = getFirstIntersection(poly1, poly2)
    if intersectionResult == None:
        return None
    newShape = [intersectionResult[0]]
    curShape = intersectionResult[1]
    nextCurShapeVtxIdx = intersectionResult[2]
    
    #while newShape.count(newShape[0]) == 1:
    #while len(newShape) < 100+len(poly1)+len(poly2):
    while bruh(newShape):
        
        
        intersectionResult = segmentIntersectsPolygon((newShape[-1], curShape[nextCurShapeVtxIdx]), poly2 if curShape == poly1 else poly1, ignore = newShape[-1])
        if intersectionResult == None:
            newShape.append(curShape[nextCurShapeVtxIdx])
            nextCurShapeVtxIdx = (nextCurShapeVtxIdx+1)%len(curShape)
        else:
            newShape.append(intersectionResult[0])
            oldShape = curShape
            curShape = poly1 if curShape == poly2 else poly2
            if pointIsInside(oldShape[intersectionResult[1][0]], poly1 if curShape == poly2 else poly2):
                nextCurShapeVtxIdx = intersectionResult[1][0]
            else:
                nextCurShapeVtxIdx = intersectionResult[1][1]
    return newShape[:-1]

if __name__ == "__main__":

    '''shape1 = Curve([[100, 50], [100, 150], [350, 150], [350, 50]])
    shape2 = Curve([[60, 110], [60, 410], [260, 410], [260, 110]])
    shape3 = Curve([[380, 300], [380, 400], [460, 400], [460, 300]])'''
    #shape1 = [[100, 50], [320, 30], [280, 100], [100, 200]]
    shape1 = [[70, 100], [250, 150], [400, 320], [100, 300]]
    shape2 = [[200, 50], [350, 200], [200, 350], [50, 450], [50, 210]]
    #shape3 = [[380, 300], [380, 400], [460, 400], [460, 300]]

    shape4 = getIntersection3(shape1, shape2)
    print("made shape")
    print(shape4)

    #space = CurveGroup([shape1, shape2, shape3, shape4])
    #space.display()
    #space.draw(500, 500)

    pygame.init()
    screen = pygame.display.set_mode((500, 500), pygame.SCALED, vsync=1)
    clock = pygame.time.Clock()
    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False

        screen.fill("black")
            
        pygame.draw.polygon(screen, (255,255, 255), shape1, width = 1)
        pygame.draw.polygon(screen, (255,255, 255), shape2, width = 1)
        #pygame.draw.polygon(screen, (0,150, 150), shape4, width = 1)
        #pygame.draw.polygon(screen, (255,255, 255), shape3, width = 1)
        for i in range(len(shape4)):
            pygame.draw.line(screen, (0,150, 150), shape4[i], shape4[(i+1)%len(shape4)], width = 2)
            vertexILabel = "Y " if segmentIntersectsPolygon((shape4[i], shape4[(i+1)%len(shape4)]), shape2) != None else "N " 
            vertexILabel += str(i)
            shapeText = freetype.Font.render(freetype.SysFont(None, 15), vertexILabel, "white")
            
            screen.blit(shapeText[0], shape4[i])
        pygame.display.update()
            
        clock.tick(10)