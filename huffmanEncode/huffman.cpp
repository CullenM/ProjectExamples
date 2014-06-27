/*
 * huffman.cpp
 *
 *  Created on: Sep 23, 2013
 *      Author: michaela & cullen
 */

#include <queue>
#include <iostream>
#include <vector>
#include <fstream>
#include <iomanip>
#include "bitChar.h"

const int asciiSize = 256;
int lCount[asciiSize];
std::string str_code[asciiSize];
const std::string magicNum="7771234777";
//std::ofstream outf("USA.txt");

struct node{
	char ch;
	int count;
	node* left;
	node* right;
};
struct list{
	char lch;
	std::string code;
	list* next;
};
list *head;

class cmp{
public:
	bool operator()(const node* lhs, const node* rhs) const
	{
		return lhs->count > rhs->count;
	}
};

node* makeNode(char ch, int count)
{
	node* tmp = new node;
	tmp->ch = ch;
	tmp->count = count;
	tmp->left = NULL;
	tmp->right = NULL;
	return tmp;
};
//linked list for holding codes
void initList(list*&head)
{
	head=new list;
	head->lch='!';
	head->code='999';
	head->next=NULL;
}

typedef std::priority_queue<node*, std::vector<node*>, cmp> mypq;

void trie(mypq& _X)
{
	while(_X.size() > 1)
	{
		node* holder = new node;
		holder->left = _X.top(); _X.pop();
		holder->right = _X.top(); _X.pop();
		holder->count = holder->left->count + holder->right->count;
		holder->ch = -1;
		_X.push(holder);
	}
}
void code(node* _X)
{
	static std::string bits = "";
	list *noo;
	if (_X->right != NULL)
	{
		bits += "1";
		code(_X->right);
		bits = bits.substr(0, bits.size() - 1);
	}
	if (_X->left != NULL)
	{
		bits += "0";
		code(_X->left);
		bits = bits.substr(0, bits.size() - 1);
	}
	if(!_X->left && !_X->right)
	{
		str_code[_X->ch] = bits;
		//outf<<_X->ch<<": "<<bits<<std::endl;
		//put codes into linked list
		noo=new list;
		noo->lch=_X->ch;
		noo->code=bits;
		noo->next=head->next;
		head->next=noo;
	}
}
void count(std::string file, int& _X){
	char letter;
	std::ifstream inf(file.c_str());

	inf >> std::noskipws;

	//Clears array
	for(int i=0;i<asciiSize;i++)
		lCount[i]=0;

	//Goes through text and counts
	while(!inf.eof()){
		inf>>letter;
		if(letter >= 0 && letter < asciiSize){
			lCount[letter]++;
			++_X;}
	}
	inf.close();
}
std::string BITSstring(std::string inFile)
{
	char input;
	std::string BITS = "";

	//Open input stream and create BITS string of entire file
	std::ifstream inf(inFile.c_str());
	inf >> std::noskipws;
	while(inf >> input)
		BITS += str_code[input];

	inf.close();

	//Append ascii 3 EOT character to signify end of text
	BITS += str_code[3];

	return BITS;
}
std::string check(std::string inFile)
{
	std::ifstream inf(inFile.c_str());
	std::string mNum, oFile;
	int lFreq;

	//check magic number
	std::getline(inf,mNum);
	if(mNum!=magicNum)
		return "flase";
	//get outfile name and populate array of frequencies
	else{
		std::getline(inf,oFile);

		for(int i=0;i<asciiSize;i++)
			lCount[i]=0;
		for(int i=0;i<asciiSize; i++)
		{
			inf>>lFreq;
			lCount[i]=lFreq;
		}
		return oFile;
	}
	inf.close();
}
void decode(std::string inFile, std::string outFile)
{
	std::ifstream inf;
	inf.open (inFile.c_str(), std::ifstream::binary);
	std::fstream outf(outFile);

	std::string dCode, blah, BITS="";
	std::string stuff;
	bitChar bchar;
	int h=0;
	list *cur;
	bool done=0, stop=0; 

	//read in from the file
	for(int t=0;t<3;t++)
		getline(inf,blah);
	while(inf>>stuff){
		bchar.setBITS(stuff);
		dCode+=stuff;}

	for(int f=0;f<dCode.length();f++)
		BITS+=bchar.getBits(dCode[f]);

	blah="";
	
	//checks input with code and outputs when they are matched
	while(h<BITS.length())
	{
		done=0;
		cur=head->next;
		blah=blah+BITS[h];

		while(cur!=NULL&&done==0)
		{
			if(cur->lch==lCount[3] && cur->code==blah)
				stop=1;
			if(cur->code==blah){
				outf<<cur->lch;
				done=1;}
			else
				cur=cur->next;
		}
		h++;
		if(done==1)
			blah="";
	}
	outf<<std::endl;
}
int main(int argc, char** argv)
{
	std::string inFile="", outFile="false", BITS="", BITSsub = "", mn = "";
	std::ofstream outf;
	std::ifstream inf;
	mypq pq;
	bitChar bchar;
	int rc,origSize = 0;
	unsigned char inChar;
	char choice;

	std::cout << "Menu..." << std::endl << "e) Encode file" << std::endl << "d) Decode file" << std::endl;
	std::cin >> choice;
	
	switch(choice)
	{
	case 'e':
		//Get input filename and set output filename
		std::cout<<"Enter File Name to Encode: "<<std::endl;
		std::cin>>inFile;

		outFile = inFile + ".mpc";

		std::cout << std::left << std::setw(17);
		std::cout << "Input filename: " << inFile << std::endl;
		std::cout << std::left << std::setw(17);
		std::cout << "Output filename:" << outFile << std::endl;
		std::cout << std::endl;

		//Open output streams
		outf.open(outFile.c_str());

		//count and populate array of letter occurrences (lCount) and add one EOT char
		count(inFile, origSize);
		if(lCount[3] == 0)
			lCount[3] = 1;

		//Output compressed file header
		outf<<magicNum<<std::endl;
		outf<<inFile<<std::endl;
		for(int i = 0; i < asciiSize; ++i)
		{
			outf << lCount[i] << " ";
		}
		outf << std::endl;

		//Create nodes based on the available ascii characters and push them into the priority queue
		for(int i = 0; i < asciiSize; ++i)
		{
			if(lCount[i] > 0)
			{
				node* tmp = makeNode(i, lCount[i]);
				pq.push(tmp);
			}
		}

		initList(head);
		//Create trie and bit codes
		trie(pq);
		code(pq.top());

		//Create string of bitcodes for actual huffman encoding and do it
		BITS = BITSstring(inFile);
		bchar.setBITS(BITS);
		outf << std::noskipws;
		rc = bchar.insertBits(outf);

		if(rc == BITS.length())
		{
			std::cout << "Encoding succsessful! :)" << std::endl;
			std::cout << "The compression ration is: " << (float)rc / ((float)origSize * 8.0) * 100.0 << "%" << std::endl;
		}
		else
		{
			std::cout << "There was an error writing the bits! :(" << std::endl;
			std::cout << "Expected: " << BITS.length() * 8 << " but got: " << rc << std::endl;
		}
		break;
	case 'd':
		//Get File name
		std::cout<<"Enter Input File Name"<<std::endl;
		std::cin>>inFile;

		//Check if file has magic number and create array
		outFile=check(inFile);
		std::cout<<outFile<<std::endl << std::endl;
		if(outFile!="false")
		{
			for(int i = 0; i < asciiSize; ++i)
			{
				if(lCount[i] > 0)
				{
					node* tmp = makeNode(i, lCount[i]);
					pq.push(tmp);
				}
			}

			//create linked list to hold character codes
			initList(head);
			//create trie and chracter codes
			trie(pq);
			code(pq.top());
			//read in from file and decode it
			decode(inFile,outFile);
			std::cout<<"File decoded"<<std::endl;
		}
		else
			std::cout<<"Not a compressed file"<<std::endl;

		break;
	default:
		std::cout << "Invalid choice...." << std::endl;
		break;
	}

	outf.close();

	system("pause");
	return 0;
}