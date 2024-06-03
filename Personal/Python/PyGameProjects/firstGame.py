import pygame

pygame.init()
screen = pygame.display.set_mode((720, 720))
clock = pygame.time.Clock()
running = True

class Ball:
    dy = 0
    dx = 0
    speed = 15
    gravity = 5
    cords = [0, 0]
    radius = 20
    display = False
    oldX = 0



while running:
    # poll for events
    # pygame.QUIT event means the user clicked X to close your window
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            Ball.display = True
            Ball.cords[0] = pygame.mouse.get_pos()[0]
            Ball.cords[1] = pygame.mouse.get_pos()[1]
            Ball.dx = 0
            Ball.dy = 0
    keys = pygame.key.get_pressed()
    if keys[pygame.K_a]:
        Ball.cords[0] -= Ball.speed
        Ball.dx = -Ball.speed
    if keys[pygame.K_d]:
        Ball.cords[0] += Ball.speed
        Ball.dx = Ball.speed
    if keys[pygame.K_w]:
        Ball.cords[1] -= Ball.speed
    if keys[pygame.K_s]:
        Ball.cords[1] += Ball.speed
    # fill the screen with a color to wipe away anything from last frame
    screen.fill("black")
    # RENDER YOUR GAME HERE
    if Ball.display:
        if Ball.cords[1] > 720 or Ball.cords[1] < 0:
            Ball.dy *= -1
        else:
            Ball.dy += Ball.gravity
        Ball.cords[1] += Ball.dy
        if Ball.cords[0] > 720 or Ball.cords[0] < 0:
            Ball.dx *= -1
        Ball.cords[0] += Ball.dx
        pygame.draw.circle(screen, "blue", Ball.cords, Ball.radius)
        
        

    # flip() the display to put your work on screen
    pygame.display.flip()

    clock.tick(60)  # limits FPS to 60

pygame.quit()