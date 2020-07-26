import pygame
import time

from pygame.locals import*
from time import sleep

# **************************************************************************
# Sprite class
# **************************************************************************
class Sprite():
    def __init__(self, type, x, y, w, h, model):
        self.type = type
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.preX = x
        self.preY = y
        self.currentImage = None
        self.model = model
        self.dead = False

    def setModel(self, model):
        self.model = model

    def isSpriteDead(self):
        return self.dead

    def rememberState(self):
        self.preX = self.x
        self.preY = self.y

    def getType(self):
        return self.type

    def loadImage(self, fileName):
        return pygame.image.load(fileName)

    def isThereASprite(self, mouseX, mouseY):
        if(mouseX < self.x):
            return False
        elif(mouseX > self.x + self.w):
            return False
        elif(mouseY < self.y):
            return False
        elif(mouseY > self.y + self.h):
            return False
        return True

    def collisonDetector(self, s2):
        if(self.x + self.w < s2.x):
            return False
        if(self.x > s2.x + s2.w):
            return False
        if(self.y + self.h < s2.y):
            return False
        if(self.y > s2.y + s2.y):
            return False
        return True

    def update(self):
        # abstract update method
        raise NotImplementedError("Please Implement this method")

    def draw(self):
        # abstract update method
        raise NotImplementedError("Please Implement this method")

    def isCollidable(self):
        raise NotImplementedError("Please Implement this method")
# **************************************************************************
# Mario class
# **************************************************************************
class Mario(Sprite):
    def __init__(self, x, y, model):
        super().__init__("m", x, y, 65, 96, model)
        self.frame = 0
        self.jumpCounter = 0
        self.vv = 12.9
        self.marioImages = []
        for i in range(5):
            self.marioImages.append(self.loadImage("mario" + str(i) + ".png"))
        self.currentImage = self.marioImages[self.frame]

    def update(self):
        self.fall()

        for i in range(len(self.model.sprites)):
            if(self.collisonDetector(self.model.sprites[i])) and (self.model.sprites[i].isCollidable() == True) and (self.model.sprites[i].type == "t"):
                self.getOutOfTube(self.model.sprites[i])
        self.imageUpdate()

    def draw(self):
        self.model.view.screen.blit(self.currentImage, (100, self.y))

    def imageUpdate(self):
        self.frame = int((abs(self.x)/10 % 5))
        self.currentImage = self.marioImages[self.frame]

    def jump(self):
        if(self.jumpCounter < 5):
            self.vv += -22.4
            self.jumpCounter += 1

    def fall(self):
        self.vv += 12
        self.y += self.vv
        if(self.y > 405):
            self.y = 405
            self.vv = 0
            self.jumpCounter = 0

    def moveMario(self, speed):
        self.x = self.x + speed
        if(self.x < -200):
            self.x = -200

    def isCollidable(self):
        return False

    def getOutOfTube(self, s):
        if((self.x + self.w > s.x) and (self.preX <= s.x)):
            self.x = s.x - self.w - 1
        if((self.x < s.x + s.w) and (self.preX >= s.x + s.w)):
            self.x = s.x  + s.w + 1
        if((self.y + self.h > s.y) and (self.preY <= s.y)):
            self.y = s.y - self.h
        if((self.y < s.y + s.h) and (self.preY >= s.y + s.h)):
            self.y = s.y + s.h + self.h
# **************************************************************************
# Tube class
# **************************************************************************
class Tube(Sprite):
    def __init__(self, type, x, y, model):
        super().__init__(type, x, y, 55, 400, model)
        self.currentImage = self.loadImage("tube.png")

    def draw(self):
        self.model.view.screen.blit(self.currentImage, (self.x - self.model.mario.x + 100, self.y))

    def update(self):
        print("tube update")

    def isCollidable(self):
        return True;

# ************************************************************************
# Goomba class
# ************************************************************************
class Goomba(Sprite):
    def __init__(self, type, x, y, model):
        super().__init__(type, x, y, 99, 118, model)
        self.currentImage = self.loadImage("goomba.png")
        self.goombaOnFireImage = self.loadImage("goombaOnFire.png")
        self.onFire = False
        self.walkRight = True
        self.onFireCounter = 0
        self.vv = 0

    def draw(self):
        self.model.view.screen.blit(self.currentImage, (self.x - self.model.mario.x + 100, self.y))

    def update(self):
        self.move()
        self.checkForCollisions()
        self.updateImage()

        if (self.onFireCounter >= 10):
            self.dead = True

    def move(self):
        self.vv += 4.5
        self.y += self.vv
        if(self.y >= 400):
            self.y = 400
            self.vv = 0

        if(self.walkRight):
            self.x += 5
        else:
            self.x += -5

    def isCollidable(self):
        return True

    def checkForCollisions(self):
        for i in range(len(self.model.sprites)):
            if(self.collisonDetector(self.model.sprites[i])):
                if(self.model.sprites[i].isCollidable() and self.model.sprites[i].type != self.type):
                    self.walkRight = not self.walkRight
                    self.getOutOfTube(self.model.sprites[i])
                elif(self.model.sprites[i].type == "f"):
                    self.onFire = True
                    self.model.sprites[i].dead = True

    def getOutOfTube(self, s):
        if((self.x + self.w > s.x) and (self.preX <= s.x)):
            self.x = s.x - self.w - 1;
        elif((self.x < s.x + s.w) and (self.preX >= s.x + s.w)):
            self.x = s.x + s.w + 1;
        else:
            print("How did I get in here?");

    def updateImage(self):
        if(self.onFire):
            self.currentImage = self.goombaOnFireImage
            self.onFireCounter += 1


# ************************************************************************
# Fireball class
# ************************************************************************
class Fireball(Sprite):
    def __init__(self, type, x, y, model):
        super().__init__(type, x, y, 45, 45, model)
        self.currentImage = self.loadImage("fireball.png")
        self.vv = 4.9
        self.hv = 20

    def draw(self):
        self.model.view.screen.blit(self.currentImage, (self.x - self.model.mario.x + 100, self.y))

    def update(self):
        self.bounce()
        self.x += self.hv
        self.isFireballOffScreen()

    def isFireballOffScreen(self):
        if(self.x >= self.model.mario.x + 700):
            self.dead = True

    def bounce(self):
        self.vv += 6.9
        self.y += self.vv

        if(self.y >= 455):
            self.y = 455
            self.vv = -30.9

    def isCollidable(self):
        return False


# ************************************************************************
# Model class
# ************************************************************************
class Model():
    def __init__(self):
        self.dest_x = 0
        self.dest_y = 0
        self.sprites = []
        self.mario = Mario(100, 400, self)
        self.sprites.append(self.mario)
        self.sprites.append(Tube("t", 300, 350, self))
        self.sprites.append(Goomba("g", 450, 350, self))
        self.sprites.append(Tube("t", 700, 350, self))

        self.rect = (800, 600)
        self.view = None

    def setView(self, view):
        self.view = view

    def update(self):
        for i in range(len(self.sprites)):
            self.sprites[i].update()
        self.removeSprites()

    def removeSprites(self):
        for i in range(len(self.sprites) -1, -1, -1):
            if(self.sprites[i].isSpriteDead()):
                del self.sprites[i]


    def rememberState(self):
        for i in range(len(self.sprites)):
            self.sprites[i].rememberState()


    def addTubeSprite(self, x, y):
        self.sprites.append(Tube("t", x, y, self))


    def editTube(self, tuple):
        clickedOnSprite = False

        for i in range(len(self.sprites) -1, -1, -1):
            if (self.sprites[i].isThereASprite(int(tuple[0]), int(tuple[1]))):
                del self.sprites[i]
                clickedOnSprite = True

        if(clickedOnSprite == False):
            self.addTubeSprite(tuple[0] + self.mario.x - 100, tuple[1])

    def addFireBallSprite(self):
        this = self
        self.fireBallCount = 0;
        for i in range(len(self.sprites)):
            if(self.sprites[i].type == "f"):
                self.fireBallCount = self.fireBallCount + 1

        if(self.fireBallCount < 2):
            self.sprites.append(Fireball("f", self.mario.x + self.mario.w, self.mario.y, this))


    def editGoomba(self, tuple):
        clickedOnGoomba = False;
        for i in range(len(self.sprites) -1, -1, -1):
            if(self.sprites[i].isThereASprite(int(tuple[0]), int(tuple[1]))):
                del self.sprites[i]
                clickedOnGoomba = True

        if(clickedOnGoomba == False):
            self.addGoombaSprite(tuple[0] + self.mario.x - 100  , tuple[1])

    def addGoombaSprite(self, x, y):
        self.sprites.append(Goomba("g", x, y, self))
        print("addGoombaSprite")
        # sprites.add(new Goomba(x - 200 + mario.x, y + fix_y, this)); // scrollPosition edits the x value where the goomba is stored
# ***********************************************************************
#  View class
# ***********************************************************************
class View():
    def __init__(self, model):
        screen_size = (800,600)
        self.screen = pygame.display.set_mode(screen_size, 32)
        self.model = model
        self.turtle_image = "mario0.png"

    def update(self):
        self.screen.fill([128,255,255])
        pygame.draw.line(self.screen, (0, 0, 0), (0,500), (800,500), 2)

        for i in range(len(self.model.sprites)):
            self.model.sprites[i].draw()
        pygame.display.flip()
# ***********************************************************************
#  Controller class
# *************************************************************************
class Controller():
    def __init__(self, model):
        self.model = model
        self.keep_going = True

    def update(self):
        self.model.rememberState()
        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    self.keep_going = False

        mouse = pygame.mouse.get_pressed()
        if mouse[0]:
            self.model.editTube(pygame.mouse.get_pos())
        if mouse[2]:
            self.model.editGoomba(pygame.mouse.get_pos())

        keys = pygame.key.get_pressed()
        if keys[K_LEFT]:
            self.model.mario.moveMario(-12)
        if keys[K_RIGHT]:
            self.model.mario.moveMario(12)
        if keys[K_UP]:
            self.model.mario.jump()
        if keys[K_LCTRL] or keys[K_RCTRL]:
            self.model.addFireBallSprite()
# *********************************************************************
# Main
# *********************************************************************

# console instructions
print("Use the arrow keys to move.")
print("Press control to shoot fireballs.")
print("Press Esc to quit.")

# "Game" object creation
pygame.init()
m = Model()
v = View(m)
m.setView(v)
c = Controller(m)

# "Game" loop
while c.keep_going:
    c.update()
    m.update()
    v.update()
    sleep(0.04)
#
# # console exit instructions
print("Goodbye")
