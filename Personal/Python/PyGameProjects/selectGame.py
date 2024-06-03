import pygame
import pygame.gfxdraw
import pygame.freetype as freetype
import math
'''
User has ability to select areas as subsets of R^2
Store a collection of selected sets which user can edit
Able to reset / add / remove sets from collection
On a command:
    Generate intersections, unions of the sets

Constantly generate the topology as the user adds shapes to the screen
Using a color or depth type to distinguish sets
'''

pygame.init()
WIDTH, HEIGHT = 750, 750
mainScreen = pygame.display.set_mode((WIDTH, HEIGHT), pygame.SCALED, vsync=1)
# pygame.SCALED, vsync=1)
clock = pygame.time.Clock()


declaredShapes = []
displayRect = mainScreen.get_rect()
curShape = []
pixelSpacing = 3
endShapeTolerance = 8
modeDict = {1 : "polygon",
            2 : "freeform",
            3 : "select"}
curModeNum = 1


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
    SHAPE = (120, 120, 120)
    POINTS = (148, 229, 235)

class Text:
    size = 20
    mainFont = freetype.SysFont(None, size)
    text = "'r' to reset shapes, 'q' to toggle modes " + str(curModeNum)
    text1 = freetype.Font.render(mainFont, "'r' to reset shapes, 'q' to toggle modes " + str(curModeNum), fgcolor=Colors.ACCENT)






def overlaps(point, polygon):
    numOverlaps = 0
    for vertexIdx in range(len(polygon)):
        if point[0] > max(polygon[(vertexIdx+1)%len(polygon)][0], polygon[vertexIdx][0]):
            continue
        dy = polygon[(vertexIdx+1)%len(polygon)][1] - polygon[vertexIdx][1]
        dx = polygon[(vertexIdx+1)%len(polygon)][0] - polygon[vertexIdx][0]
        if dy == 0: # slope = 0
            if point[1] == polygon[vertexIdx][1]:
                numOverlaps += 1
        elif dx == 0: # slope is undefined
            if point[1] < max(polygon[(vertexIdx+1)%len(polygon)][1], polygon[vertexIdx][1]) and point[1] > min(polygon[(vertexIdx+1)%len(polygon)][1], polygon[vertexIdx][1]):
                numOverlaps += 1
        else:
            slope = dy/dx
            b = polygon[vertexIdx][1] - slope*polygon[vertexIdx][0]
            xIntercept = (point[1] - b)/slope
            if xIntercept >= point[0] and xIntercept >= min(polygon[(vertexIdx+1)%len(polygon)][0], polygon[vertexIdx][0]) and xIntercept < max(polygon[(vertexIdx+1)%len(polygon)][0], polygon[vertexIdx][0]):
                numOverlaps += 1
    return numOverlaps % 2 != 0

def sigmoid(shapesOverlapped):
    return (255/(1+math.exp(-0.5*(shapesOverlapped-5))), 255/(1+math.exp(-0.3*(shapesOverlapped-5))), 150, 255/(1+math.exp(-0.3*(shapesOverlapped-5))))

def drawScreen():
    mainScreen.fill(Colors.BACKGROUND, displayRect)
    text1 = freetype.Font.render(Text.mainFont, "'r' to reset shapes, 'q' to toggle modes " + str(curModeNum), fgcolor=Colors.ACCENT)
    mainScreen.blit(text1[0], (25, 25))
    for shape in declaredShapes:
        pygame.draw.polygon(mainScreen, Colors.SHAPE, shape, width = 1)
        #pygame.draw.rect(mainScreen, (50, 10, 2, 50), (pygame.draw.polygon(mainScreen, Colors.SHAPE, shape)))
        #pygame.draw.lines(mainScreen, Colors.GREEN, True, shape)
    for x in range(displayRect.left, displayRect.right + 1, pixelSpacing):
        for y in range(displayRect.top, displayRect.bottom + 1, pixelSpacing):
            shapesOverlapped = 0
            for shape in declaredShapes:
                if overlaps((x, y), shape):
                    shapesOverlapped += 1
            if shapesOverlapped > 1:
                pygame.draw.circle(mainScreen, (sigmoid(shapesOverlapped)), (x, y), 1)
    if curShape:
        for vertexIdx in range(len(curShape)-1):
            pygame.draw.line(mainScreen, Colors.LINE, curShape[vertexIdx], curShape[vertexIdx+1])
        pygame.draw.line(mainScreen, Colors.LINE, curShape[-1], pygame.mouse.get_pos())
    #if displayRect:
    #pygame.draw.rect(mainScreen, (10, 255, 5, 10), displayRect, width = 1)
        #pygame.draw.polygon(mainScreen, (10, 255, 5, 10), [displayRect.topleft, displayRect.bottomleft, displayRect.bottomright, displayRect.topright], width=1)
        
    


def boundRectangle(polygon):
    maxX = max([cord[0] for cord in polygon])
    minX = min([cord[0] for cord in polygon])
    maxY = max([cord[1] for cord in polygon])
    minY = min([cord[1] for cord in polygon])
    left = minX
    top = minY
    width = maxX - minX
    height = maxY - minY
    return pygame.Rect(left, top, width, height)

def handleMouseDown(mouseX, mouseY):
    global curShape, displayRect, declaredShapes, curModeNum
    match modeDict[curModeNum]:
        case "polygon":
            if not curShape:
                curShape.append((mouseX, mouseY))
                displayRect = pygame.Rect(mouseX, mouseY, 0, 0)
            elif math.hypot(mouseX-curShape[0][0], mouseY-curShape[0][1]) < endShapeTolerance:
                if len(curShape) > 1:
                    displayRect = boundRectangle(curShape)
                    declaredShapes.append(curShape.copy())
                curShape.clear()
            else:
                curShape.append((mouseX, mouseY))
                displayRect = pygame.Rect(mouseX, mouseY, 0, 0)
        case "freeform":
            if not curShape:
                curShape.append((mouseX, mouseY))
                displayRect = pygame.Rect(mouseX, mouseY, 0, 0)
        case _:
            pass

def handleMouseMotion(mouseX, mouseY):
    global curShape, displayRect, declaredShapes, curModeNum
    match modeDict[curModeNum]:
        case "polygon":
            displayRect.union_ip(boundRectangle((curShape[-1], (mouseX, mouseY))))
        case "freeform":
            
            if math.hypot(mouseX-curShape[0][0], mouseY-curShape[0][1]) < endShapeTolerance:
                if len(curShape) > 2:
                    displayRect = boundRectangle(curShape)
                    declaredShapes.append(curShape.copy())
                    curShape.clear()
            elif math.hypot(mouseX-curShape[-1][0], mouseY-curShape[-1][1]) > endShapeTolerance:
                displayRect.union_ip(boundRectangle((curShape[-1], (mouseX, mouseY))))
                curShape.append((mouseX, mouseY))
                
                
        case _:
            pass

def handleKeydown(event):
    global curShape, displayRect, declaredShapes, curModeNum
    if event.key == pygame.K_r:
        declaredShapes.clear()
        curShape.clear()
        displayRect = mainScreen.get_rect()
    if event.key == pygame.K_q:
        curModeNum = (curModeNum+1)%len(modeDict)
        displayRect = mainScreen.get_rect()
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

running = True
'''mainScreen.fill(Colors.BACKGROUND)
mainScreen.blit(Text.text1, (25, 25))
pygame.display.flip()'''
while running:
    mouseX, mouseY = pygame.mouse.get_pos()
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        if event.type == pygame.MOUSEBUTTONDOWN:
            handleMouseDown(mouseX, mouseY)
            
        if event.type == pygame.MOUSEMOTION and curShape:
            handleMouseMotion(mouseX, mouseY)
            
        if event.type == pygame.KEYDOWN:
            handleKeydown(event)
            
    
    drawScreen()
    
    pygame.display.update(displayRect)
        
    clock.tick(30)


    