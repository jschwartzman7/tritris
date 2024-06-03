import random

class Shape:

    def __init__(self, cords, shapesIntersecting):
        self.cords = cords
        self.color = (int(random.random()*256), int(random.random()*256), int(random.random()*256))
        self.origShapesIntersecting = shapesIntersecting
