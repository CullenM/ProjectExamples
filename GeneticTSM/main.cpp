//Cullen McDevitt
//Traveling Salesman: Genetic Algorithm

#include <iostream>
#include <iomanip>
#include <fstream>
#include <cmath>
#include <math.h>
#include <stdio.h>

using namespace std;

const int CNUM=16;
const int POP=100;
const int GENS=1000000;
struct city{
	int name;
	int x;
	int y;
};
struct indiv{
	int name;
	int cityN[CNUM];
	int cityX[CNUM];
	int cityY[CNUM];
	int score;
	double distance;
};

indiv population [POP];

void init(){
	city* cities=new city[CNUM];
	indiv* population=new indiv[POP];
}
void fillCities(city cities[]){
	cities[0].name=0;cities[0].x=0; cities[0].y=0;
	cities[1].name=1;cities[1].x=5; cities[1].y=2;
	cities[2].name=2;cities[2].x=16; cities[2].y=3;
	cities[3].name=3;cities[3].x=13; cities[3].y=5;
	cities[4].name=4;cities[4].x=15; cities[4].y=9;
	cities[5].name=5;cities[5].x=10; cities[5].y=10;
	cities[6].name=6;cities[6].x=4; cities[6].y=9;
	cities[7].name=7;cities[7].x=6; cities[7].y=12;
	cities[8].name=8;cities[8].x=12; cities[8].y=13;
	cities[9].name=9;cities[9].x=9; cities[9].y=14;
	cities[10].name=10;cities[10].x=16; cities[10].y=20;
	cities[11].name=11;cities[11].x=18; cities[11].y=18;
	cities[12].name=12;cities[12].x=2; cities[12].y=5;
	cities[13].name=13;cities[13].x=7; cities[13].y=5;
	cities[14].name=14;cities[14].x=2; cities[14].y=16;
	cities[15].name=15;cities[15].x=11; cities[15].y=18;
}
void print(){
	ofstream outf("result.txt");
	int l=0;

	outf<<"Best Run: Cities ";
	//for(int l=0; l<POP; l++){	
		//outf<<population[l].name<<": ";
		for(int m=0;m<CNUM;m++){
			outf<<population[l].cityN[m]<<", ";}
		outf<<"Distance= "<<population[l].distance;
		cout<<population[l].name<<"="<<population[l].distance<<endl;
	//}
}
double ABdist(int curr,int a, int b){
	double dist=0;
	int ax=0,bx=0,ay=0,by=0,x=0,y=0,q=0;
	if(population[curr].cityN[a]==population[curr].cityN[b]){
		dist=25;}
		//cout<<population[curr].cityN[a]<<"+"<<population[curr].cityN[b]<<"=0"<<endl;}
	else{
		x=abs(population[curr].cityX[a]-population[curr].cityX[b]);
		y=abs(population[curr].cityY[a]-population[curr].cityY[b]);
		q= (x*x)+(y*y);
		dist=sqrt(double(q));}
	return dist;
}
int checkRep(int curr, int count,int repeats){
	if(count==CNUM-1)
		return repeats;
	else{
		for(int n=count+1;n<CNUM-1;n++){
				if(population[curr].cityN[count]==population[curr].cityN[n]){
					repeats=repeats+1;}
		}
		checkRep(curr,count+1,repeats);}
}
void score(int curr){
	int k=0,score=0,reps=0;
	double dist=0;
	for(int k=0;k<CNUM;k++){
		dist=dist+ABdist(curr,k,k+1);}
	population[curr].distance=dist;
	score=int(dist);
	if(population[curr].cityN[0]==population[curr].cityN[15]){
		score=score-25;}
	reps=checkRep(curr,0,0);
	if(reps==0){
		score=score-25;}
	else{reps=reps*35;}
	score=score+reps;
	population[curr].score=score;
}
void initP(indiv*& tmp){
	tmp= new indiv;

	tmp->name=0;
	tmp->distance=0;
	tmp->score=0;
}
void generate(int num, city cities[]){
	int c;

	for(int j=POP; j>POP-(num+1);j--){
		if(population[j].name==0){
			population[j].name=j;}
		for(int i=0; i<CNUM;i++){
			c=0;
			c = rand() % 15 + 1;
			population[j].cityN[i]=cities[c].name;
			population[j].cityX[i]=cities[c].x;
			population[j].cityY[i]=cities[c].y;
			population[j].score=0;}
			score(j);
	}
	//print();
}
void sort(){
    indiv tmp;

	for(int p=1;p<POP;p++){
		for(int o=0;o<POP-p;o++){
			if(population[o].score>population[o+1].score){
				tmp=population[o];
				population[o]=population[o+1];
				population[o+1]=tmp;}}
	}
}
void breed(){
	int rate=POP*.4;
	int arate=0;
	int brate=rate/2;
	
	for(int r=0;r<rate;r++){
		for(int q=0;q<CNUM;q++){
			if(q%2==0||q==CNUM){
				population[brate].cityN[q]=population[arate].cityN[q];
				population[brate].cityX[q]=population[arate].cityX[q];
				population[brate].cityY[q]=population[arate].cityY[q];}
		}
		brate+=1;
		arate+=1;
	}
}
void mutate(city cities[]){
	int rate=POP*.4,crate=POP*.2,mut;
	for(int t=rate;t<POP-crate;t++){
		for(int u=0;u<CNUM;u++){
			mut=rand() % 1;
			if(mut==0){
				population[t].cityN[u]=cities[rand()%15+1].name;
				population[t].cityX[u]=cities[rand()%15+1].x;
				population[t].cityY[u]=cities[rand()%15+1].y;}}
	}
}
void killOff(city cities[]){
	int crate=POP*.2;
	generate(crate,cities);
}
void run(int gens,city cities[]){
	int v=0;

	generate(POP,cities);
	sort();
	print();
	cout<<endl;
	for(int v=0;v<gens;v++){
		breed();
		mutate(cities);
		killOff(cities);
		for(int s=0;s<POP;s++){score(s);}
		sort();
		if(v%1000==0){
			cout<<v<<": "<<population[0].name<<" "<<population[0].score<<endl<<endl;}}
	print();
}

void main(){
	city cities[CNUM];

	fillCities(cities);
	run(GENS,cities);

	system("pause");
}