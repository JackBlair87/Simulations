import noise
import numpy as np
from PIL import Image
import pygame as pg

from Terrain import Terrain

#Default 1000
HEIGHT = 800
WIDTH = 1440
BLACK = (0, 0, 0)

SCALE = 300.0
OCTAVES = 5
PERSISTANCE = 0.5
LACUNARITY = 2.0 #Increases fine detail

def quitGame(): #Quits Pygame and Python
    pg.quit()
    quit()
    
def backgroundInputCheck(eventList): #Constantly checks for quits and enters
    for event in eventList:
            if event.type == pg.QUIT:
                quitGame()
            elif event.type == pg.KEYDOWN:
                if event.key == pg.K_SPACE:
                    return False
                elif event.key == pg.K_RETURN:
                    return False
                elif event.key == pg.K_ESCAPE:
                    quitGame()
    return True

def createLand(initWaterLevel, increment):
    global WIDTH, HEIGHT, SCALE, OCTAVES, PERSISTANCE, LACUNARITY
    SEED = np.random.randint(0, 100)
    world = Terrain(HEIGHT, WIDTH)
    world.generateTopTerrain(SCALE, OCTAVES, PERSISTANCE, LACUNARITY, SEED)
    print("Land Generated")

    waterLevel = initWaterLevel
 
    while waterLevel < world.findMaxHeight():
        backgroundInputCheck(pg.event.get())
        world.generateTopColor(waterLevel, waterLevel+0.045, waterLevel+0.24, waterLevel+0.32, waterLevel+0.46, waterLevel+0.54)
        image = pg.surfarray.make_surface(world.topColorTerrain).convert()
        
        screen.fill(BLACK)
        screen.blit(image, (0, 0))
        
        waterLevel += increment
        clock.tick(30)
        pg.display.flip()

def showLand(waterLevel):
    global WIDTH, HEIGHT, SCALE, OCTAVES, PERSISTANCE, LACUNARITY
    SEED = np.random.randint(0, 100)
    world = Terrain(HEIGHT, WIDTH)
    world.generateTopTerrain(SCALE, OCTAVES, PERSISTANCE, LACUNARITY, SEED)
    world.raiseTerrain(-0.3)
    print("Land Generated")
    
    while True:
        backgroundInputCheck(pg.event.get())
        world.generateTopColor(waterLevel, waterLevel+0.045, waterLevel+0.24, waterLevel+0.32, waterLevel+0.46, waterLevel+0.54)
        image = pg.surfarray.make_surface(world.topColorTerrain).convert()
        screen.fill(BLACK)
        screen.blit(image, (0, 0))
        
        clock.tick(30)
        pg.display.flip()

#world.generateFrontTerrain()
#world.generateFrontColor()
###world.drawSideImage().show()

pg.init()
screen = pg.display.set_mode([WIDTH, HEIGHT])
pg.display.set_caption("Perlin Noise Terrain Generation")
clock = pg.time.Clock()
createLand(0.01, 0.005)
showLand(0.01)

pg.quit()
quit()