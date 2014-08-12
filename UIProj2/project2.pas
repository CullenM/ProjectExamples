unit Project2;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, Vcl.StdCtrls, Vcl.ExtCtrls,math,
  Vcl.Imaging.jpeg;

type
  TForm1 = class(TForm)
    title: TLabel;
    houseBox: TEdit;
    payBox: TEdit;
    housePrice: TLabel;
    dwnPay: TLabel;
    interest: TLabel;
    description: TLabel;
    term: TLabel;
    Label1: TLabel;
    answer: TLabel;
    background: TImage;
    clear: TButton;
    Calculate: TButton;
    exit: TButton;
    titlePgBttn: TButton;
    affordPgBttn: TButton;
    intrCBx: TComboBox;
    termLBx: TListBox;
    procedure blank();
    procedure calculateClick(Sender: TObject);
    procedure exitClick(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure titlePgBttnClick(Sender: TObject);
    procedure affordPgBttnClick(Sender: TObject);
    procedure clearClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

uses titlePage, affordPage;
procedure TForm1.blank();
begin
  houseBox.Text:='';
  payBox.Text:='';
  intrCBx.Text:='';
  answer.Visible:=false;
end;
procedure TForm1.calculateClick(Sender: TObject);
var
  R : real;
  i : real;
  n : real;
  P : real;
  D : real;
  term: string;
  interest: string;
begin
  term:=termLbx.Items[termLbx.ItemIndex];
  term:=copy(term,0,2);
  interest:=intrCBx.Items[intrCBx.ItemIndex];
  interest:=copy(interest,0,3);
  if interest='' then
    interest:=intrCBx.Text;
  i:=strtofloat(interest);
  n:=strtofloat(term);
  P:=strtofloat(houseBox.Text);
  D:=strtofloat(payBox.Text);

  i:=(i*0.01)/12;
  n:=n*12;

  R:=((P-D)*i*Power((1+i),n))/(Power((1+i),n)-1);
  answer.Visible:=true;
  answer.Alignment:=taCenter;
  answer.Caption:='Your monthly mortage payment is: '+#13#10+'$'+formatfloat('0.00',R);
end;
procedure TForm1.clearClick(Sender: TObject);
begin
blank();
end;
procedure TForm1.exitClick(Sender: TObject);
begin
application.Terminate;
end;
procedure TForm1.FormCreate(Sender: TObject);
begin
  blank();
end;
procedure TForm1.titlePgBttnClick(Sender: TObject);
begin
  titleForm.show;
  form1.Hide;
end;
procedure TForm1.affordPgBttnClick(Sender: TObject);
begin
  affordform.show;
  form1.Hide;
end;

end.