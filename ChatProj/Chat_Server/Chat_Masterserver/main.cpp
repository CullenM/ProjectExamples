//MINE

#pragma comment(lib, "Ws2_32.lib")

#include <WinSock2.h>
#include <Windows.h>
#include <iostream>
#include <string>

using namespace std;

SOCKADDR_IN addr;

SOCKET sListen;
SOCKET sConnect;
SOCKET oConnect;
SOCKET* Connections;

int addrlen = sizeof(addr);
int ConCounter = 0;

//struct for message
struct buff
{
	string id;
	char nickname[64];
	char Message[256];
};

buff buffer[64];

//sends out message to other clients
void sThread(int ID)
{
	bool g = true;
	char* line= new char;
	char* Recm = new char[256];
	char* Send = new char[sizeof(buff)];
	string fullMessage;
	DWORD QUIT;
	
	ZeroMemory(line,sizeof(line));
	ZeroMemory(Send, sizeof(buff));
	do
	{
		ZeroMemory(Recm, 256);
		if(recv(Connections[ID], Recm, 256, NULL))
		{
			fullMessage.clear();
			if(Recm=="QUIT")
			{
				cout<<buffer[ID].nickname<<": Disconnected"<<endl;
				for(int c=ID;c<ConCounter;c++)
					buffer[ID+1].id=ID;
				ExitThread(QUIT);
			}
			else
			{
				fullMessage.clear();				
				ZeroMemory(buffer[ID].Message,256);
			
				fullMessage.append(buffer[ID].nickname);
				fullMessage.append(":");
				fullMessage.append(Recm);

				for(int i = 0;i < fullMessage.length();i++)
					Send[i] = fullMessage[i];
				cout<<"<"<<ID<<">"<<fullMessage<<endl;

				for(int a=0; a!= ConCounter; a++)
				{
					if (Connections[a]==Connections[ID])
						send(Connections[a],line,sizeof(line),NULL);
					else
					send(Connections[a],Send,fullMessage.length(),NULL);
				}
			}
		}
	}
	while(g==true);
}

//sets up socket
int initializeWinSock()
{
	int rVal = 0;
	WSAData wsaData;
	WORD DllVersion = MAKEWORD(2,1);
	rVal = WSAStartup(DllVersion, &wsaData);

	return rVal;
}
void connect()
{
	bool g = true;
	int ID=0,port,rVal = 0;
	char* IP;
	char* name=new char[64];
	string something;

	IP="192.168.7.119"; //127.0.0.1 <localhost
	port=1234;

	//socket stuff
	rVal = initializeWinSock();
	if(rVal != 0)
		cout<<"Error:Winsock startup failed"<<endl;

	Connections = (SOCKET*)calloc(64, sizeof(SOCKET));

	sListen = socket(AF_INET, SOCK_STREAM, NULL);
	sConnect = socket(AF_INET, SOCK_STREAM, NULL);

	addr.sin_addr.s_addr = inet_addr(IP);
	addr.sin_port        = htons(port);
	addr.sin_family      = AF_INET;

	cout<<"IP Address: "<<IP<<endl;
	cout<<"Port Number: "<<port<<endl;

	bind(sListen, (SOCKADDR*)&addr, sizeof(addr));	
	listen(sListen, 64);

	//connected to client
	do
	{
		ZeroMemory(name, 64);
		if(sConnect = accept(sListen, (SOCKADDR*)&addr, &addrlen))
		{
			Connections[ConCounter] = sConnect;					
			ID=ConCounter;
			buffer[ID].id=ID;

			recv(Connections[ID],name,64,NULL);
			something=name;
			memcpy(buffer[ID].nickname,name,something.length()-1);
			cout<<"Client connected: "<<buffer[ID].nickname<<endl;

			ConCounter = ConCounter + 1;
			CreateThread(NULL, NULL, (LPTHREAD_START_ROUTINE) sThread, (LPVOID)(ConCounter - 1), NULL, NULL);
		}
	}
	while(g==true);
}
void main()
{
	cout<<"SERVER"<<endl;
	connect();
}