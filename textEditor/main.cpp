//Cullen McDevitt
//Text editor ToA

#include <iostream>
#include <windows.h>
#include <stdlib.h>
#include <conio.h>
#include <time.h>
#include <string>

using namespace std;

const int maxLetter=80;
const int maxLine=23;
bool ins=false;

struct line{
	int letter[maxLetter];
	int length;
	int id;
	line* prev;
	line* next;
};
line* head;

void createBuffer(line*& newLine, line*& oldLine, int count){
	if(count!=25)
	{
		newLine=new line;
		newLine->length=0;
		newLine->id=count;
		if(newLine->id==1)
			newLine->prev=NULL;
		else
		newLine->prev=oldLine;
		newLine->next=NULL;
		newLine->letter[0]=0;
		for(int i=1;i<maxLetter;i++)
			newLine->letter[i]=32;
		count++;
		createBuffer(newLine->next, newLine, count);
	}
}
void gotoxy(int x, int y){  
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	COORD point;
   	point.X = x-1;
	point.Y = y-1;     
   	SetConsoleCursorPosition(hConsole, point);
   	return;
}
int getch(void){ 
	int response;
	cout << flush;
  	response = _getch();
  	HANDLE hConsole = GetStdHandle(STD_INPUT_HANDLE);
  	FlushConsoleInputBuffer(hConsole);
  	return response;
}
void getPoint(int count,line* current){
	string overS;
	if(ins==true)
		overS="Off";
	else
		overS="On";
	gotoxy(1,24);
	cout<<"                                                                               ";
	gotoxy(1,24);
	cout<<" Column: "<<count<<" Row: "<<current->id<<"   Overstrike mode: "<<overS;
}
void bruteSearch(string sItem,int& count, line*& current)
{
	int i,j,S=sItem.size()-1,R=current->length+1;
	for(i=0,j=0;j<S && i<R;i++,j++){
		while(current->letter[i]!=sItem[j]&&i<R){
			i-=j-1;
			j=0;}
	}
	if(j==S){
		count=i-S;
		gotoxy(count+1,current->id);}
	else if(current->next!=NULL){
		current=current->next;
		bruteSearch(sItem,count,current);}
	else{
		cout<<"Item could not be found                                                        ";}
}
int searchF(int& count,line*& current){
	string sItem="";
	char ch=0;
	current=head;

	gotoxy(1,24);
	cout<<"                                                                               ";
	gotoxy(1,24);
	cout<<"Search for: ";
	while(ch!=13){
		ch=_getch();
		if(ch==27)
			return 0;
		sItem+=ch;
		gotoxy(13,24);
		cout<<sItem;}
		bruteSearch(sItem,count,current);
		return -1;
}
void print(int count, line* current)
{
	int line=23;
	while(current!=NULL){
		gotoxy(1,current->id);
		for(int p=0;p<current->length+1;p++)
			cout<<char(current->letter[p]);
		current=current->next;		
		line--;}	
}
void setPointA(int posX,int posY){
	gotoxy(posX,posY);
}
void setPointR(int count,line* current){
	gotoxy(count,current->id);
}
void insert(int ch,int& count,line*& current){
	int place=current->length+1;

	if(count==maxLetter){
		count+=1;
		insert(13,count,current);
		current=current->next;
		count=0;}
	if(ins==true){
		while(place!=count){
			current->letter[place]=current->letter[place-1];
			place--;}
		current->letter[count]=ch;
		current->length=current->length+1;}
	else{
		current->letter[count]=ch;
		current->length=count;}
}
void del(int count, line*& current){
	int place=current->length+1;

	while(count-1!=place){
		current->letter[count]=current->letter[count+1];
		count++;}
	current->length=place-1;
	print(count,current);
}
int checkCh(int ch,int &count, line* &current)
{
	int ch2,s=0;
	switch (ch)
	{
		case 13: //enter
			count+=1;
			insert(13,count,current);
			current=current->next;
			count=0;
			return 0;
		break;
		case 8: //backspace
			count=count-1;
			del(count,current);
			return 0;
		break;
		case 6: //ctrl+F
			s=searchF(count,current);
			return s;
		break;
		case 224: ch2=_getch();
			switch(ch2)
			{
			case 83: //delete
				del(count,current);
				return 0;
			break;
			case 72: //up arrow
				if(current->prev!=NULL)
					current=current->prev;
				if(current->length<count)
					count=current->length;
				setPointR(count,current);
				return 0;
			break;
			case 80:  //down arrow 
				if(current->next->length!=0){
					current=current->next;
					return 0;}
				if(current->length<count)
					count=current->length;
				setPointR(count,current);
				return 0;
			break;
			case 75:  //left arrow
				count=count-1;
				setPointR(count,current);
				return 0;
			break;
			case 77:  //right arrow
				count=count+1;
				setPointR(count,current);
				return 0;
			break;
			case 71: //home
				count=1;
				setPointA(count,current->id);
				return 0;
			break;
			case 79: //end 
				count=current->length;
				setPointA(count+1,current->id);		
				return 0;
			break;
			case 119: //ctrl+home
				count=1;
				current=head;
				setPointA(count,current->id);	
				return 0;
			break;
			case 117: //ctrl+end
				while(current->next->length!=0)
					current=current->next;
				count=current->length+1;
				setPointA(count,current->id);
				return 0;
			break;
			case 82: //insert
				if(ins==false)
					ins=true;
				else
					ins=false;
				return 0;
			break;}
		default:
			return ch;
	}
}
void main ()
{ 
	int ch;
	bool stop=false;
	int count=1;

	createBuffer(head,head->prev,1);
	line* current=head;
	count=0;

	while(stop!=true){
		ch=_getch();		
		ch=checkCh(ch,count,current);
		if(ch>0){
			insert(ch,count,current);
			print(count,current);
			count++;}
		if(ch!=-1){
			getPoint(count,current);
			gotoxy(count,current->id);}
	}
}
