import selectGame2

class GameMode:


    def mouseDown():
        pass
    def mouseMotion():
        pass
    def keyDown():
        pass

class polygonMode(GameMode):

    def mouseDown():
        if not self.curShape:
                self.curShape.append((mouseX, mouseY))
                self.displayRectangle = pygame.Rect(mouseX, mouseY, 0, 0)
            elif math.hypot(mouseX-self.curShape[0][0], mouseY-self.curShape[0][1]) < self.closeShapeDistance:
                if len(self.curShape) > 1:
                    self.displayRectangle = self.boundRectangle(self.curShape)
                    self.declaredShapes.append(self.curShape.copy())
                    self.declaredColors.append((int(random.random()*256), int(random.random()*256), int(random.random()*256)))
                self.curShape.clear()
            else:
                self.curShape.append((mouseX, mouseY))
                self.displayRectangle = pygame.Rect(mouseX, mouseY, 0, 0)
    def mouseMotion():
        pass
    def keyDown():
        pass

class ellipseMode(GameMode):

    def mouseDown():
        pass
    def mouseMotion():
        pass
    def keyDown():
        pass

class freeFormMode(GameMode):

    def mouseDown():
        if not self.curShape:
                self.curShape.append((mouseX, mouseY))
                self.displayRectangle = pygame.Rect(mouseX, mouseY, 0, 0)
    def mouseMotion():
        pass
    def keyDown():
        pass
