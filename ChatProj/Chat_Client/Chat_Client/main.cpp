#pragma comment(lib, "Ws2_32.lib")

#include <WinSock2.h>
#include <Windows.h>
#include <iostream>
#include <string>

using namespace std;

SOCKADDR_IN addr;
SOCKET sConnect;

//Struct for message
struct buff
{
	string ID;
	char nickname[64];
	char Message[256];
};

//Outputs message
void cThread()
{
	bool g = true;
	buff sbuffer;
	char buffer[sizeof(sbuffer)] = {0};
	char* nick= new char[64];
	char* mess= new char[256];
	char* nID = new char[64];
	
	do
	{
		/*if(recv(sConnect, buffer, sizeof(sbuffer), NULL))
		{
			memcpy(&sbuffer, buffer, sizeof(sbuffer));
			cout<< sbuffer.nickname << " " << sbuffer.Message <<endl;
			//cout<<sbuffer.Message<<endl;
		}*/
		ZeroMemory(mess,256);
		if(recv(sConnect,mess,256,NULL))
		{
			memcpy(sbuffer.Message,mess,256);
			cout<<sbuffer.Message<<endl;

			//recv(sConnect,nick,64,NULL);
			//recv(sConnect,mess,256,NULL);
			/*memcpy(sbuffer.nickname,nick,64);
			memcpy(sbuffer.Message,mess,256);
			cout<<"<"<<sbuffer.nickname<<"> "<<sbuffer.Message<<endl;*/
			//cout<<nick<<" & "<<mess<<endl;
		}
	}
	while(g==true);
}
void connect()
{
	bool f=true, g=true;
	char serverAns, ipAddr [256], errMessage [1024];	
	string mess;
	string nickname;
	short portNum;
	int length,error,rVal=0;
	buff buffer;	

	cout<<"CONNECT TO A SERVER? [Y/N]"<<endl;
	do
	{
	cin>>serverAns;
	if(serverAns=='Y'||serverAns=='y')
	{
		f=false;
		cout<<"ENTER IP ADDRESS"<<endl;
		cin>>ipAddr;
		cout<<"ENTER PORT NUMBER"<<endl;
		cin>>portNum;

		sConnect = socket(AF_INET, SOCK_STREAM, NULL); //(IPv4,TCP,NULL)
		addr.sin_addr.s_addr = inet_addr(ipAddr);	
		addr.sin_port = htons(portNum);				
		addr.sin_family = AF_INET;

		rVal = connect(sConnect, (SOCKADDR*)&addr, sizeof(addr));

		if(rVal != 0)
		{
			error=WSAGetLastError();
			FormatMessage(FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS |
				FORMAT_MESSAGE_MAX_WIDTH_MASK, NULL, error,
				MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), (LPSTR)errMessage, 1024, NULL);

			cout<<" Error: Could not connect to server"<<endl<<errMessage<<endl;
		}
		else
		{
			//recv(sConnect, cID, 64, NULL); //recieves ID from server
			//buffer.ID = atoi(cID);

			cout << "Connected" <<endl;
			cout << "You are Client No: " << buffer.ID <<endl;
			cout<<"Enter a nickname:  ";
			ZeroMemory(buffer.nickname, 256);
			getline(cin,nickname);
			for(int i=0;i<nickname.length();i++)
			{
				buffer.nickname[i]=nickname[i];
			}	

			send(sConnect, buffer.nickname, nickname.length(), NULL);
			CreateThread(NULL, NULL, (LPTHREAD_START_ROUTINE) cThread, NULL, NULL, NULL);
		
			//Reads in message
			do
			{			
				ZeroMemory(buffer.Message, 256);
				mess.clear();

				getline(cin,mess);
				mess.append("\n");
				for(int i = 0;i < mess.length();i++)
					buffer.Message[i] = mess[i];
				send(sConnect, buffer.Message,mess.length(), NULL);
				//send(sConnect, buffer.nickname, 64,NULL);
			}
			while(g==true);
		}
	}
	else if(serverAns=='N'||serverAns=='n')
		f=false;
	else
		cout<<"Answer must be 'Y' or 'N'."<<endl;
	}
	while(f==true);
}
void join()
{
	bool g = true;
	int rVal = 0;
	buff buffer;
	char* cID = new char[64];

	//Sets up socket
	WSAData wsaData;
	WORD DllVersion = MAKEWORD(2,1);
	rVal = WSAStartup(DllVersion, &wsaData);
	if(rVal != 0)
		cout<<"Error:Winsock startup failed"<<endl;

	connect();
}

void main()
{
	join();

	system("pause");
}