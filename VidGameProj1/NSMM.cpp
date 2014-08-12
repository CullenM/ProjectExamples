//============================================================================
// Name        : NSMM.cpp
// Author      : Thayne Wilson Cullen McDevitt
// Version     :
// Copyright   : Your copyright notice
// Description : Not So Mega Man
//============================================================================

//The headers
#include "SDL/SDL.h"
#include "SDL/SDL_image.h"
#include <fstream>
#include <string>
#include <vector>

#include "tile.hpp"

using namespace std;

const int SCREEN_WIDTH = 640;
const int SCREEN_HEIGHT = 480;
const int GUY_WIDTH=66;
const int GUY_HEIGHT=73;

//The dimensions of the level
const int LEVEL_WIDTH = 640; // 1280
const int LEVEL_HEIGHT = 480; // 960

//Tile constants
//const int TILE_WIDTH = 15;
//const int TILE_HEIGHT = 15;
//const int TOTAL_TILES = 1376;
//const int TILE_SPRITES = 150;

const int TEST = 1;
Uint32 now = 0;

//The different tile sprites
// black rocks sprite 1
const int s1topleft = 0;
const int s1topright = 1;
const int s1botleft = 2;
const int s1botright = 3;
// black rocks star sprite
const int s2topleft = 4;
const int s2topright = 5;
const int s2botleft = 6;
const int s2botright = 7;
// regular dirt sprite
const int dirtblocktopleft = 8;
const int dirtblocktopright = 9;
const int dirtblockbotleft = 10;
const int dirtblockbotright = 11;
// regular dirt sprite 2
const int dirtblock2topleft = 12;
const int dirtblock2topright = 13;
const int dirtblock2botleft = 14;
const int dirtblock2botright = 15;
// regular dirt sprite 3
const int dirtblock3topleft = 16;
const int dirtblock3topright = 17;
const int dirtblock3botleft = 18;
const int dirtblock3botright = 19;
// snowy dirt sprite
const int snowdirttopleft = 20;
const int snowdirttopright = 21;
const int snowdirtbotleft = 22;
const int snowdirtbotright = 23;
// snowy dirt sprite 2
const int snowdirt2topleft = 24;
const int snowdirt2topright = 25;
const int snowdirt2botleft = 26;
const int snowdirt2botright = 27;
// snowy dirt sprite 3
const int snowdirt3topleft = 28;
const int snowdirt3topright = 29;
const int snowdirt3botleft = 30;
const int snowdirt3botright = 31;
// snow platform
const int snowplatformtopleft = 32;
const int snowplatformtopright = 33;
const int snowplatformbotleft = 34;
const int snowplatformbotright = 35;
// snow platform 2
const int snowplatform2topleft = 36;
const int snowplatform2topright = 37;
const int snowplatform2botleft = 38;
const int snowplatform2botright = 39;
// snow platform 3
const int snowplatform3topleft = 40;
const int snowplatform3topright = 41;
const int snowplatform3botleft = 42;
const int snowplatform3botright = 43;
// left and right ends of snow platforms
const int snowplatformleftend = 44;
const int snowplatformleftend2 = 45;
const int snowplatformrightend = 46;
const int snowplatformrightend2 = 47;
// snowman
const int snowmantopleft = 93;
const int snowmantopright = 94;
const int snowmanmidleft = 95;
const int snowmanmidright = 96;
const int snowmanbotleft = 97;
const int snowmanbotright = 98;
// transparency tile
const int transparent = 99; // use this to create gaps

//The surfaces
SDL_Surface *dot = NULL;
SDL_Surface *screen = NULL;
SDL_Surface *tileSheet = NULL;
SDL_Surface *background1 = NULL;
SDL_Surface *background2 = NULL;
SDL_Surface *guy = NULL;


SDL_Rect wall[10];
//SDL_Rect wall2;
//SDL_Rect wall3;
//SDL_Rect wall4;
//SDL_Rect wall5;
//SDL_Rect wall6;
//SDL_Rect wall7;
SDL_Rect death;
SDL_Rect clip[4];

//Sprite from the tile sheet
SDL_Rect clips[ TILE_SPRITES ];

//The event structure
SDL_Event event;

//The camera
//SDL_Rect camera = { 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT }; needs to be implemented

//The tile
class Tile
{
    private:
    //The attributes of the tile
    SDL_Rect box;

    //The tile type
    int type;

    public:
    //Initializes the variables
    Tile( int x, int y, int tileType );

    //Shows the tile
    void show();

    //Get the tile type
    int get_type();

    //Get the collision box
    SDL_Rect get_box();
};

class Guy
{
private:
	int x,y;
	std::vector<SDL_Rect> box;
	int xVel, yVel;
	void shift_boxes();
public:
	Guy(int X, int Y);
	void handle_input();
	//std::vector<SDL_Rect> &rect
	void move();
	void jump();
	void show(int stan);
	std::vector<SDL_Rect> &get_rects();
};


SDL_Surface *load_image( std::string filename )
{
    SDL_Surface* loadedImage = NULL;
    SDL_Surface* optimizedImage = NULL;

    loadedImage = IMG_Load( filename.c_str() );

    if( loadedImage != NULL )
    {
        optimizedImage = SDL_DisplayFormat( loadedImage );
        SDL_FreeSurface( loadedImage );
        if( optimizedImage != NULL )
        {
            SDL_SetColorKey( optimizedImage, SDL_SRCCOLORKEY, SDL_MapRGB( optimizedImage->format, 255, 0, 255 ) );
        }
    }
    return optimizedImage;
}

void apply_surface( int x, int y, SDL_Surface* source, SDL_Surface* destination, SDL_Rect* clip = NULL )
{
    SDL_Rect offset;

    offset.x = x;
    offset.y = y;

    SDL_BlitSurface( source, clip, destination, &offset );
}

bool check_collison(std::vector<SDL_Rect> &A, SDL_Rect B)
{
	int leftA, leftB;
	int rightA, rightB;
	int topA, topB;
	int bottomB, bottomA;

	leftB=B.x;
	rightB=B.x+B.w;
	topB=B.y;
	bottomB=B.y+B.h;
	for(int Abox=0; Abox<A.size(); Abox++)
	{
		leftA=A[Abox].x;
		rightA=A[Abox].x+A[Abox].w;
		topA=A[Abox].y;
		bottomA=A[Abox].y+A[Abox].h;
		/*for(int Bbox=0; Bbox<B.size(); Bbox++)
		{
			leftB=B[Bbox].x;
			rightB=B[Bbox].x+B[Bbox].w;
			topB=B[Bbox].y;
			bottomB=B[Bbox].y+B[Bbox].h;
			if(((bottomA<=topB)||(topA>=bottomB)||(rightA<=leftB)||(leftA>=rightB))==false)
				return true;
		}*/
		if(((bottomA<=topB)||(topA>=bottomB)||(rightA<=leftB)||(leftA>=rightB))==false)
			return true;
	}
	return false;
}

bool init()
{
    //Initialize all SDL subsystems
    if( SDL_Init( SDL_INIT_EVERYTHING ) == -1 )
    {
        return false;
    }

    //Set up the screen
    screen = SDL_SetVideoMode( SCREEN_WIDTH, SCREEN_HEIGHT, 16, SDL_ANYFORMAT | SDL_HWSURFACE | SDL_DOUBLEBUF );

    //If there was an error in setting up the screen
    if( screen == NULL )
    {
        return false;
    }

    //Set the window caption
    SDL_WM_SetCaption( "NSMM", NULL );

    //If everything initialized fine
    return true;
}

bool load_files()
{
    //Load the tile sheet
    tileSheet = load_image( "./images/area03_level_tiles.png" );
    background1 = load_image("./images/area03_bkg0.png");
    background2 = load_image("./images/area03_bkg1.png");
    guy=load_image("./images/soldier.bmp");

    //If there was a problem in loading the tiles
    if( tileSheet == NULL )
    {
        return false;
    }

    //If everything loaded fine
    return true;
}

void clean_up( Tile *tiles[] )
{
    //Free the surfaces
    SDL_FreeSurface( tileSheet );

    //Free the tiles
    for( int t = 0; t < TOTAL_TILES; t++ )
    {
        delete tiles[ t ];
    }

    //Quit SDL
    SDL_Quit();
}

Guy::Guy(int X, int Y)
{
	x=X;
	y=Y;
	xVel=0;
	yVel=0;
	box.resize(13);
	box[0].w=15;
	box[0].h=2;
	box[1].w=19;
	box[1].h=2;
	box[2].w=23;
	box[2].h=4;
	box[3].w=27;
	box[3].h=12;
	box[4].w=29;
	box[4].h=2;
	box[5].w=33;
	box[5].h=2;
	box[6].w=39;
	box[6].h=4;
	box[7].w=43;
	box[7].h=2;
	box[8].w=47;
	box[8].h=6;
	box[9].w=49;
	box[9].h=2;
	box[10].w=51;
	box[10].h=3;
	box[11].w=49;
	box[11].h=2;
	box[12].w=41;
	box[12].h=16;
	shift_boxes();
}
void Guy::shift_boxes()
{
	int r=0;
	for(int set=0; set<box.size(); set++)
	{
		box[set].x=x+(GUY_WIDTH-box[set].w)/2;
		box[set].y=y+r;
		r+=box[set].h;
	}
}
void Guy::handle_input()
{
	if(event.type==SDL_KEYDOWN)
	{
		switch (event.key.keysym.sym)
		{
			case SDLK_UP: yVel-=1; break;
			case SDLK_DOWN: yVel+=1; break;
			case SDLK_LEFT: xVel-=1; break;
			case SDLK_RIGHT: xVel+=1; break;
			default : break;
		}
	}
	if(event.type==SDL_KEYUP)
	{
		switch(event.key.keysym.sym)
		{
			case SDLK_UP: yVel+=1; break;
			case SDLK_DOWN: yVel-=1; break;
			case SDLK_LEFT: xVel+=1; break;
			case SDLK_RIGHT: xVel-=1; break;
			default : break;
		}
	}
}
void Guy::jump()
{
	y-=25;
	yVel-=2;
	while(y+GUY_HEIGHT<SCREEN_HEIGHT)
	{
		//yVel+=2;
		y+=1;
		shift_boxes();
	}
}
void Guy::move()
{
	x+=xVel;
	shift_boxes();
	if((x<0)||(x+GUY_WIDTH>SCREEN_WIDTH))
	{
		x-=xVel;
		shift_boxes();
	}
	else{
	for(int i=0;i<10;i++){
		if(check_collison(box,wall[i]))
			x-=xVel;
			shift_boxes();
		}
	}
	y+=yVel;
	shift_boxes();
	if((y<0)||(y+GUY_HEIGHT>405))
	{
		y-=yVel;
		shift_boxes();
	}
	else{
	for(int i=0;i<10;i++){
		if(check_collison(box,wall[i]))
			y-=yVel;
			shift_boxes();
		}
	}
	//if(moving == true){yVel -= -0.05;}
}
void Guy::show(int stan)
{
	if(yVel!=0||xVel!=0){now=SDL_GetTicks();}
	stan=(now/200)%4;
	apply_surface(x,y,guy,screen,&clip[stan]);
}
std::vector<SDL_Rect> &Guy::get_rects()
{
	return box;
}
void clip_tiles()
{
	// black rock tile clips
	clips[s1topleft].x = 0;
	clips[s1topleft].y = 0;
	clips[s1topleft].w = TILE_WIDTH;
	clips[s1topleft].h = TILE_HEIGHT;

	clips[s1topright].x = 16;
	clips[s1topright].y = 0;
	clips[s1topright].w = TILE_WIDTH;
	clips[s1topright].h = TILE_HEIGHT;

	clips[s1botleft].x = 0;
	clips[s1botleft].y = 16;
	clips[s1botleft].w = TILE_WIDTH;
	clips[s1botleft].h = TILE_HEIGHT;

	clips[s1botright].x = 16;
	clips[s1botright].y = 16;
	clips[s1botright].w = TILE_WIDTH;
	clips[s1botright].h = TILE_HEIGHT;

	// black rock star tile clips
	clips[s2topleft].x = 32;
	clips[s2topleft].y = 0;
	clips[s2topleft].w = TILE_WIDTH;
	clips[s2topleft].h = TILE_HEIGHT;

	clips[s2topright].x = 48;
	clips[s2topright].y = 0;
	clips[s2topright].w = TILE_WIDTH;
	clips[s2topright].h = TILE_HEIGHT;

	clips[s2botleft].x = 32;
	clips[s2botleft].y = 16;
	clips[s2botleft].w = TILE_WIDTH;
	clips[s2botleft].h = TILE_HEIGHT;

	clips[s2botright].x = 48;
	clips[s2botright].y = 16;
	clips[s2botright].w = TILE_WIDTH;
	clips[s2botright].h = TILE_HEIGHT;

	// dirt tile clips
	clips[dirtblocktopleft].x = 160;
	clips[dirtblocktopleft].y = 64;
	clips[dirtblocktopleft].w = TILE_WIDTH;
	clips[dirtblocktopleft].h = TILE_HEIGHT;

	clips[dirtblocktopright].x = 176;
	clips[dirtblocktopright].y = 64;
	clips[dirtblocktopright].w = TILE_WIDTH;
	clips[dirtblocktopright].h = TILE_HEIGHT;

	clips[dirtblockbotleft].x = 160;
	clips[dirtblockbotleft].y = 80;
	clips[dirtblockbotleft].w = TILE_WIDTH;
	clips[dirtblockbotleft].h = TILE_HEIGHT;

	clips[dirtblockbotright].x = 176;
	clips[dirtblockbotright].y = 80;
	clips[dirtblockbotright].w = TILE_WIDTH;
	clips[dirtblockbotright].h = TILE_HEIGHT;

	clips[dirtblock2topleft].x = 192;
	clips[dirtblock2topleft].y = 64;
	clips[dirtblock2topleft].w = TILE_WIDTH;
	clips[dirtblock2topleft].h = TILE_HEIGHT;

	clips[dirtblock2topright].x = 208;
	clips[dirtblock2topright].y = 64;
	clips[dirtblock2topright].w = TILE_WIDTH;
	clips[dirtblock2topright].h = TILE_HEIGHT;

	clips[dirtblock2botleft].x = 192;
	clips[dirtblock2botleft].y = 80;
	clips[dirtblock2botleft].w = TILE_WIDTH;
	clips[dirtblock2botleft].h = TILE_HEIGHT;

	clips[dirtblock2botright].x = 208;
	clips[dirtblock2botright].y = 80;
	clips[dirtblock2botright].w = TILE_WIDTH;
	clips[dirtblock2botright].h = TILE_HEIGHT;

	clips[dirtblock3topleft].x = 224;
	clips[dirtblock3topleft].y = 64;
	clips[dirtblock3topleft].w = TILE_WIDTH;
	clips[dirtblock3topleft].h = TILE_HEIGHT;

	clips[dirtblock3topright].x = 240;
	clips[dirtblock3topright].y = 64;
	clips[dirtblock3topright].w = TILE_WIDTH;
	clips[dirtblock3topright].h = TILE_HEIGHT;

	clips[dirtblock3botleft].x = 224;
	clips[dirtblock3botleft].y = 80;
	clips[dirtblock3botleft].w = TILE_WIDTH;
	clips[dirtblock3botleft].h = TILE_HEIGHT;

	clips[dirtblock3botright].x = 240;
	clips[dirtblock3botright].y = 80;
	clips[dirtblock3botright].w = TILE_WIDTH;
	clips[dirtblock3botright].h = TILE_HEIGHT;
    // snowy dirt tile clips
	clips[snowdirttopleft].x = 160;
	clips[snowdirttopleft].y = 32;
	clips[snowdirttopleft].w = TILE_WIDTH;
	clips[snowdirttopleft].h = TILE_HEIGHT;

	clips[snowdirttopright].x = 176;
	clips[snowdirttopright].y = 32;
	clips[snowdirttopright].w = TILE_WIDTH;
	clips[snowdirttopright].h = TILE_HEIGHT;

	clips[snowdirtbotleft].x = 160;
	clips[snowdirtbotleft].y = 48;
	clips[snowdirtbotleft].w = TILE_WIDTH;
	clips[snowdirtbotleft].h = TILE_HEIGHT;

	clips[snowdirtbotright].x = 176;
	clips[snowdirtbotright].y = 48;
	clips[snowdirtbotright].w = TILE_WIDTH;
	clips[snowdirtbotright].h = TILE_HEIGHT;

	clips[snowdirt2topleft].x = 192;
	clips[snowdirt2topleft].y = 32;
	clips[snowdirt2topleft].w = TILE_WIDTH;
	clips[snowdirt2topleft].h = TILE_HEIGHT;

	clips[snowdirt2topright].x = 208;
	clips[snowdirt2topright].y = 32;
	clips[snowdirt2topright].w = TILE_WIDTH;
	clips[snowdirt2topright].h = TILE_HEIGHT;

	clips[snowdirt2botleft].x = 192;
	clips[snowdirt2botleft].y = 48;
	clips[snowdirt2botleft].w = TILE_WIDTH;
	clips[snowdirt2botleft].h = TILE_HEIGHT;

	clips[snowdirt2botright].x = 208;
	clips[snowdirt2botright].y = 48;
	clips[snowdirt2botright].w = TILE_WIDTH;
	clips[snowdirt2botright].h = TILE_HEIGHT;

	clips[snowdirt3topleft].x = 224;
	clips[snowdirt3topleft].y = 32;
	clips[snowdirt3topleft].w = TILE_WIDTH;
	clips[snowdirt3topleft].h = TILE_HEIGHT;

	clips[snowdirt3topright].x = 240;
	clips[snowdirt3topright].y = 32;
	clips[snowdirt3topright].w = TILE_WIDTH;
	clips[snowdirt3topright].h = TILE_HEIGHT;

	clips[snowdirt3botleft].x = 224;
	clips[snowdirt3botleft].y = 48;
	clips[snowdirt3botleft].w = TILE_WIDTH;
	clips[snowdirt3botleft].h = TILE_HEIGHT;

	clips[snowdirt3botright].x = 240;
	clips[snowdirt3botright].y = 48;
	clips[snowdirt3botright].w = TILE_WIDTH;
	clips[snowdirt3botright].h = TILE_HEIGHT;
    // snow platform tile clips
	clips[snowplatformtopleft].x = 160;
	clips[snowplatformtopleft].y = 0;
	clips[snowplatformtopleft].w = TILE_WIDTH;
	clips[snowplatformtopleft].h = TILE_HEIGHT;

	clips[snowplatformtopright].x = 176;
	clips[snowplatformtopright].y = 0;
	clips[snowplatformtopright].w = TILE_WIDTH;
	clips[snowplatformtopright].h = TILE_HEIGHT;

	clips[snowplatformbotleft].x = 160;
	clips[snowplatformbotleft].y = 16;
	clips[snowplatformbotleft].w = TILE_WIDTH;
	clips[snowplatformbotleft].h = TILE_HEIGHT;

	clips[snowplatformbotright].x = 176;
	clips[snowplatformbotright].y = 16;
	clips[snowplatformbotright].w = TILE_WIDTH;
	clips[snowplatformbotright].h = TILE_HEIGHT;
	// snow platform 2 tile clips
	clips[snowplatform2topleft].x = 192;
	clips[snowplatform2topleft].y = 0;
	clips[snowplatform2topleft].w = TILE_WIDTH;
	clips[snowplatform2topleft].h = TILE_HEIGHT;

	clips[snowplatform2topright].x = 208;
	clips[snowplatform2topright].y = 0;
	clips[snowplatform2topright].w = TILE_WIDTH;
	clips[snowplatform2topright].h = TILE_HEIGHT;

	clips[snowplatform2botleft].x = 192;
	clips[snowplatform2botleft].y = 16;
	clips[snowplatform2botleft].w = TILE_WIDTH;
	clips[snowplatform2botleft].h = TILE_HEIGHT;

	clips[snowplatform2botright].x = 208;
	clips[snowplatform2botright].y = 16;
	clips[snowplatform2botright].w = TILE_WIDTH;
	clips[snowplatform2botright].h = TILE_HEIGHT;
	// snow platform 3 tile clips
	clips[snowplatform3topleft].x = 224;
	clips[snowplatform3topleft].y = 0;
	clips[snowplatform3topleft].w = TILE_WIDTH;
	clips[snowplatform3topleft].h = TILE_HEIGHT;

	clips[snowplatform3topright].x = 240;
	clips[snowplatform3topright].y = 0;
	clips[snowplatform3topright].w = TILE_WIDTH;
	clips[snowplatform3topright].h = TILE_HEIGHT;

	clips[snowplatform3botleft].x = 224;
	clips[snowplatform3botleft].y = 16;
	clips[snowplatform3botleft].w = TILE_WIDTH;
	clips[snowplatform3botleft].h = TILE_HEIGHT;

	clips[snowplatform3botright].x = 240;
	clips[snowplatform3botright].y = 16;
	clips[snowplatform3botright].w = TILE_WIDTH;
	clips[snowplatform3botright].h = TILE_HEIGHT;
    // snow platform end clips
	clips[snowplatformleftend].x = 16;
	clips[snowplatformleftend].y = 80;
	clips[snowplatformleftend].w = TILE_WIDTH;
	clips[snowplatformleftend].h = TILE_HEIGHT;

	clips[snowplatformleftend2].x = 16;
	clips[snowplatformleftend2].y = 96;
	clips[snowplatformleftend2].w = TILE_WIDTH;
	clips[snowplatformleftend2].h = TILE_HEIGHT;

	clips[snowplatformrightend].x = 0;
	clips[snowplatformrightend].y = 80;
	clips[snowplatformrightend].w = TILE_WIDTH;
	clips[snowplatformrightend].h = TILE_HEIGHT;

	clips[snowplatformrightend2].x = 0;
	clips[snowplatformrightend2].y = 96;
	clips[snowplatformrightend2].w = TILE_WIDTH;
	clips[snowplatformrightend2].h = TILE_HEIGHT;

	// Snowman tile clips
	clips[snowmantopleft].x = 160;
	clips[snowmantopleft].y = 96;
	clips[snowmantopleft].w = TILE_WIDTH;
	clips[snowmantopleft].h = TILE_HEIGHT;

	clips[snowmantopright].x = 176;
	clips[snowmantopright].y = 96;
	clips[snowmantopright].w = TILE_WIDTH;
	clips[snowmantopright].h = TILE_HEIGHT;

	clips[snowmanmidleft].x = 160;
	clips[snowmanmidleft].y = 112;
	clips[snowmanmidleft].w = TILE_WIDTH;
	clips[snowmanmidleft].h = TILE_HEIGHT;

	clips[snowmanmidright].x = 176;
	clips[snowmanmidright].y = 112;
	clips[snowmanmidright].w = TILE_WIDTH;
	clips[snowmanmidright].h = TILE_HEIGHT;

	clips[snowmanbotleft].x = 160;
	clips[snowmanbotleft].y = 128;
	clips[snowmanbotleft].w = TILE_WIDTH;
	clips[snowmanbotleft].h = TILE_HEIGHT;

	clips[snowmanbotright].x = 176;
	clips[snowmanbotright].y = 128;
	clips[snowmanbotright].w = TILE_WIDTH;
	clips[snowmanbotright].h = TILE_HEIGHT;
	// Transparent tile
	clips[transparent].x = 112;
	clips[transparent].y = 128;
	clips[transparent].w = TILE_WIDTH;
	clips[transparent].h = TILE_HEIGHT;
}

bool set_tiles( Tile *tiles[])
{
    //The tile offsets
    int x = 0, y = 0;

    //Open the map
    std::ifstream map("./map1pt1.map");

    //If the map couldn't be loaded
    if( map == NULL )
    {
        return false;
    }

    //Initialize the tiles
    for( int t = 0; t < TOTAL_TILES; t++ )
    {
        //Determines what kind of tile will be made
        int tileType = -1;

        //Read tile from map file
        map >> tileType;

        //If the was a problem in reading the map
        if( map.fail() == true )
        {
            //Stop loading map
            map.close();
            return false;
        }

        //If the number is a valid tile number
        if( ( tileType >= 0 ) && ( tileType < TILE_SPRITES ) )
        {
            tiles[ t ] = new Tile( x, y, tileType );
        }
        //If we don't recognize the tile type
        else
        {
            //Stop loading map
            map.close();
            return false;
        }

        //Move to next tile spot
        x += TILE_WIDTH;

        //If we've gone too far
        if( x >= LEVEL_WIDTH )
        {
            //Move back
            x = 0;

            //Move to the next row
            y += TILE_HEIGHT;
        }
    }

    //Close the file
    map.close();

    //If the map was loaded fine
    return true;
}


Tile::Tile( int x, int y, int tileType )
{
    //Get the offsets
    box.x = x;
    box.y = y;

    //Set the collision box
    box.w = TILE_WIDTH;
    box.h = TILE_HEIGHT;

    //Get the tile type
    type = tileType;
}

void Tile::show()
{
    //If the tile is on screen
        //Show the tile
        apply_surface( box.x, box.y, tileSheet, screen, &clips[ type ] );
}

int Tile::get_type()
{
    return type;
}

SDL_Rect Tile::get_box()
{
    return box;
}

void display(Guy myGuy,int stan)
{
	//SDL_FillRect(screen,&screen->clip_rect,SDL_MapRGB(screen->format,47,153,202));
	//SDL_FillRect(screen,&wall1,SDL_MapRGB(screen->format,203,102,90));
	//SDL_FillRect(screen,&wall2,SDL_MapRGB(screen->format,203,102,90));
	myGuy.show(stan);
}

int main( int argc, char* args[] )
{

    bool done = false;
	int stan=0;
	//Uint32 now = 0;
	Guy myGuy(0,0);
	// collision box dimensions
	wall[0].x=0;
	wall[0].y=285;
	wall[0].w=107;
	wall[0].h=25;
	wall[1].x=180;
	wall[1].y=345;
	wall[1].w=92;
	wall[1].h=25;
	wall[2].x=0;
	wall[2].y=210;
	wall[2].w=45;
	wall[2].h=25;
	wall[3].x=135;
	wall[3].y=150;
	wall[3].w=150;
	wall[3].h=25;
	wall[4].x=400;
	wall[4].y=255;
	wall[4].w=97;
	wall[4].h=25;
	wall[5].x=600;
	wall[5].y=270;
	wall[5].w=30;
	wall[5].h=100;
	// death box
	death.x=0;
	death.y=0;
	death.w=0;
	death.h=0;
	// dimensions of main character
	clip[0].x=0;
	clip[0].y=0;
	clip[0].w=66;
	clip[0].h=73;
	clip[1].x=65;
	clip[1].y=0;
	clip[1].w=65;
	clip[1].h=72;
	clip[2].x=130;
	clip[2].y=0;
	clip[2].w=65;
	clip[2].h=72;
	clip[3].x=195;
	clip[3].y=0;
	clip[3].w=65;
	clip[3].h=72;
    //The tiles that will be used
    Tile *tiles[ TOTAL_TILES ];

    //Initialize
    if( init() == false )
    {
        return 1;
    }

    //Load the files
    if( load_files() == false )
    {
        return 1;
    }

    //Clip the tile sheet
    clip_tiles();

    //Set the tiles
    if( set_tiles( tiles ) == false )
    {
        return 1;
    }

    while( done == false )
    {
        apply_surface(0,0,background1,screen);
        apply_surface(0,0,background2,screen);
        display(myGuy,stan);
        //While there's events to handle
        while( SDL_PollEvent( &event ) )
        {
        	myGuy.handle_input();
        	//now=SDL_GetTicks();
            if( event.type == SDL_QUIT )
            {
                done = true;
            }
        }
        myGuy.move();
		display(myGuy,stan);
        //Show the tiles
        for( int t = 0; t < TOTAL_TILES; t++ )
        {
            tiles[ t ]->show();
        }
        // flip the page
        SDL_Flip(screen);
    }

    //Clean up
    clean_up( tiles );

    return 0;
}
