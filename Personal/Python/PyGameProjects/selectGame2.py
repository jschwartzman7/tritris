import pygame
import pygame.gfxdraw
import pygame.freetype as freetype
import math
import random
import statistics as st
import CurveGroups
import Shape
#import GameModes
'''
User has ability to select areas as subsets of R^2
Store a collection of selected sets which user can edit
Able to reset / add / remove sets from collection
On a command:
    Generate intersections, unions of the sets

Constantly generate the topology as the user adds shapes to the screen
Using a color or depth type to distinguish sets
'''

class Colors:
    RED = (255, 0, 0)
    GREEN = (0, 255, 0)
    BLUE = (0, 0, 255)
    GREY = (50, 50, 50)
    WHITE = (255, 255, 255)
    BLACK = (0, 0, 0)
    ORANGE = (255, 165, 0)
    YELLOW = (255, 255, 0)
    PURPLE = (128, 0, 128)
    BACKGROUND = (60, 60, 80)
    ACCENT = (118, 174, 219)
    LINE = (122, 122, 122)
    SHAPE = (20, 20, 20)
    POINTS = (148, 229, 235)




class SelectGame:

    def __init__(self, width, height, closeShapeDistance, fps):

        pygame.init()
        self.screen = pygame.display.set_mode((width, height), pygame.SCALED, vsync=1)
        self.clock = pygame.time.Clock()
        self.declaredShapes = []
        self.displayRectangle = self.screen.get_rect()
        self.shapeInProgress = []
        self.closeShapeDistance = closeShapeDistance
        self.modeDict = {1 : "polygon",
                         2: "ellipse",
                         3 : "freeform",
                         4 : "select"}
        self.curModeNum = 1
        #self.curGameMode = GameModes
        self.colors = Colors
        self.fps = fps
        self.shapeJustAdded = False
        self.fontSize = 20
        self.mainFont = freetype.SysFont(None, self.fontSize)

        text = "'r' to reset shapes, 'q' to toggle modes " + self.modeDict[self.curModeNum]
        self.text1 = freetype.Font.render(self.mainFont, "'r' to reset shapes, 'q' to toggle modes " + str(self.curModeNum), fgcolor=self.colors.ACCENT)
        





    def overlaps(self, point, polygon): # Check how many times the ray y = point[1] crosses polygon for all x >= point[0]
        edgeOverlaps = 0
        xValuesOverlapped = set()
        for vtxIdx, vertex in enumerate(polygon):
            if point[0] > max(polygon[(vtxIdx+1)%len(polygon)][0], vertex[0]):
                continue
            if point[1] < min(polygon[(vtxIdx+1)%len(polygon)][1], vertex[1]) or point[1] > max(polygon[(vtxIdx+1)%len(polygon)][1], vertex[1]):
                continue
            dy = polygon[(vtxIdx+1)%len(polygon)][1] - vertex[1]
            dx = polygon[(vtxIdx+1)%len(polygon)][0] - vertex[0]
            if dy == 0:
                edgeOverlaps += 1
                continue
            if dx == 0:
                if vertex[0] not in xValuesOverlapped:
                    xValuesOverlapped.add(vertex[0])
                    edgeOverlaps += 1
                continue
            slope = dy/dx
            b = vertex[1] - slope*vertex[0]
            xValIntersect = (point[1] - b)/slope
            #if xIntercept >= point[0] and xIntercept >= min(polygon[(vtxIdx+1)%len(polygon)][0], vertex[0]) and xIntercept < max(polygon[(vtxIdx+1)%len(polygon)][0], vertex[0]):
            if xValIntersect >= point[0] and xValIntersect not in xValuesOverlapped:
                xValuesOverlapped.add(xValIntersect)
                edgeOverlaps += 1
        

        return edgeOverlaps % 2 != 0


    def drawScreen(self):
        self.screen.fill(Colors.BACKGROUND, self.displayRectangle)
        text1 = freetype.Font.render(self.mainFont, "'r' to reset shapes, 'q' to toggle modes " + str(self.curModeNum), fgcolor=Colors.ACCENT)
        self.screen.blit(text1[0], (25, 25))
        orderedShapes = self.declaredShapes.copy()
        if orderedShapes:
            orderedShapes.sort(key=lambda shape: shape.origShapesIntersecting)
        for shapeNum, shape in enumerate(orderedShapes):
            shapeText = freetype.Font.render(self.mainFont, "" + str(shapeNum+1), shape.color)
            shapeRect = self.boundRectangle(shape.cords)
            self.screen.blit(shapeText[0], (shapeRect.left, shapeRect.top))

            
            pygame.draw.polygon(self.screen, shape.color, shape.cords, width = shape.origShapesIntersecting+1)
            #pygame.draw.rect(mainScreen, (50, 10, 2, 50), (pygame.draw.polygon(mainScreen, Colors.SHAPE, shape)))
            #pygame.draw.lines(mainScreen, Colors.GREEN, True, shape)
     
        if self.shapeInProgress:
            for vertexIdx in range(len(self.shapeInProgress)-1):
                pygame.draw.line(self.screen, Colors.LINE, self.shapeInProgress[vertexIdx], self.shapeInProgress[vertexIdx+1])
            pygame.draw.line(self.screen, Colors.LINE, self.shapeInProgress[-1], pygame.mouse.get_pos())
        #if displayRect:
        #pygame.draw.rect(self.screen, (10, 255, 5, 10), self.displayRectangle, width = 1)
            #pygame.draw.polygon(mainScreen, (10, 255, 5, 10), [displayRect.topleft, displayRect.bottomleft, displayRect.bottomright, displayRect.topright], width=1)
            
        


    def boundRectangle(self, polygon):
        maxX = max([cord[0] for cord in polygon])
        minX = min([cord[0] for cord in polygon])
        maxY = max([cord[1] for cord in polygon])
        minY = min([cord[1] for cord in polygon])
        left = minX
        top = minY
        width = maxX - minX
        height = maxY - minY
        return pygame.Rect(left, top, width, height)


            

    def addIntersections(self, newShape):
        newShapes = []
        for shape in self.declaredShapes:
            intersection = CurveGroups.getIntersection3(newShape, shape.cords)
            if intersection != None:
                newShapes.append(Shape.Shape(intersection, shape.origShapesIntersecting + 1))
        self.declaredShapes.extend(newShapes)

    def mouseDown(self, mouseX, mouseY):
        match self.modeDict[self.curModeNum]:
            case "polygon":
                if not self.shapeInProgress:
                    self.shapeInProgress.append((mouseX, mouseY))
                    self.displayRectangle = pygame.Rect(mouseX, mouseY, 0, 0)
                elif math.hypot(mouseX-self.shapeInProgress[0][0], mouseY-self.shapeInProgress[0][1]) < self.closeShapeDistance:
                    if len(self.shapeInProgress) > 1:
                        self.displayRectangle = self.boundRectangle(self.shapeInProgress)
                        #self.declaredShapes.append(self.addIntersections(self.curShape))
                        self.shapeJustAdded = True
                        
                        self.declaredShapes.append(Shape.Shape(self.shapeInProgress.copy(), 0))
                    self.shapeInProgress.clear()
                else:
                    self.shapeInProgress.append((mouseX, mouseY))
                    self.displayRectangle = pygame.Rect(mouseX, mouseY, 0, 0)
            case "ellipse":
                self.shapeInProgress.append((mouseX, mouseY))
            case "freeform":
                if not self.shapeInProgress:
                    self.shapeInProgress.append((mouseX, mouseY))
                    self.displayRectangle = pygame.Rect(mouseX, mouseY, 0, 0)
            case "select":
                pass

    def mouseMotion(self, mouseX, mouseY):
        match self.modeDict[self.curModeNum]:
            case "polygon":
                if self.shapeInProgress:
                    self.displayRectangle.union_ip(self.boundRectangle((self.shapeInProgress[-1], (mouseX, mouseY))))
            case "freeform":
                
                if math.hypot(mouseX-self.shapeInProgress[0][0], mouseY-self.shapeInProgress[0][1]) < self.closeShapeDistance:
                    if len(self.shapeInProgress) > 2:
                        self.displayRectangle = self.boundRectangle(self.shapeInProgress)
                        self.declaredShapes.append(self.shapeInProgress.copy())
                        self.shapeInProgress.clear()
                elif math.hypot(mouseX-self.shapeInProgress[-1][0], mouseY-self.shapeInProgress[-1][1]) > self.closeShapeDistance:
                    self.displayRectangle.union_ip(self.boundRectangle((self.shapeInProgress[-1], (mouseX, mouseY))))
                    self.shapeInProgress.append((mouseX, mouseY))
                    
                    
            case _:
                pass

    def keyDown(self, event):
        if event.key == pygame.K_r:
            self.declaredShapes.clear()
            self.shapeInProgress.clear()
            self.displayRectangle = self.screen.get_rect()
        if event.key == pygame.K_q:
            self.curModeNum = (self.curModeNum+1)%len(self.modeDict)
            self.displayRectangle = self.screen.get_rect()
    '''
    dispRect = boundShape
    addShape to declared
    A and D

    addMouseCords to curShape
    dispRect = mouseCords
    (not A) or B

    clear curShape
    A and (not B)

    '''
    def play(self):
        running = True
        while running:
            mouseX, mouseY = pygame.mouse.get_pos()
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                if event.type == pygame.MOUSEBUTTONDOWN:
                    self.mouseDown(mouseX, mouseY)
                if event.type == pygame.MOUSEMOTION:
                    self.mouseMotion(mouseX, mouseY)
                if event.type == pygame.KEYDOWN:
                    self.keyDown(event)
                    
            if self.shapeJustAdded:
                self.addIntersections(self.shapeInProgress)
            self.drawScreen()
            
            pygame.display.update(self.displayRectangle)
                
            self.clock.tick(self.fps)


myGame = SelectGame(1200, 900, 10, 30)
myGame.play()
            
