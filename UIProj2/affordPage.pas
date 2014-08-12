unit affordPage;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, Vcl.StdCtrls,math,project2,
  Vcl.Imaging.jpeg, Vcl.ExtCtrls;

type
  TaffordForm = class(TForm)
    aExitBttn: TButton;
    aMortBttn: TButton;
    aTitleBttn: TButton;
    aTitle: TLabel;
    salaryEd: TEdit;
    billsEd: TEdit;
    aIntrCbx: TComboBox;
    aTermLbx: TListBox;
    aDownEd: TEdit;
    aCalcBttn: TButton;
    aClearBttn: TButton;
    aAnswerLb: TLabel;
    aSalaryLb: TLabel;
    aBillsLb: TLabel;
    aIntrLb: TLabel;
    aTermLb: TLabel;
    aDownLb: TLabel;
    Label1: TLabel;
    Label2: TLabel;
    Image1: TImage;
    procedure aMortBttnClick(Sender: TObject);
    procedure aTitleBttnClick(Sender: TObject);
    procedure aCalcBttnClick(Sender: TObject);
    procedure aExitBttnClick(Sender: TObject);
    procedure aClearBttnClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  affordForm: TaffordForm;

implementation

{$R *.dfm}

uses titlePage;

procedure TaffordForm.aCalcBttnClick(Sender: TObject);
var
  P,R,i,n,D,bills,sal : real;
  term, interest: string;
begin
  term:=atermLbx.Items[atermLbx.ItemIndex];
  term:=copy(term,0,2);
  interest:=aintrCBx.Items[aintrCBx.ItemIndex];
  interest:=copy(interest,0,3);
  if interest='' then
    interest:=aintrCBx.Text;
  i:=strtofloat(interest);
  n:=strtofloat(term);
  D:=strtofloat(aDownEd.Text);
  bills:=strtofloat(billsEd.Text);
  sal:=strtofloat(salaryEd.Text);

  i:=(i*0.01)/12;
  n:=n*12;

  R:=(sal-bills)*0.29;
  P:=(R*(Power((1+i),n)-1)/(i*Power((1+i),n)))+D;

  aAnswerLb.Visible:=true;
  aAnswerLb.Alignment:=taCenter;
  aAnswerLb.Caption:='You can afford a house worth: '+#13#10+'$'+formatfloat('0.00',P);
end;
procedure TaffordForm.aClearBttnClick(Sender: TObject);
begin
  salaryEd.Text:='';
  billsEd.Text:='';
  aIntrCbx.Text:='';
  aDownEd.Text:='';

  aAnswerLb.Visible:=false;
end;
procedure TaffordForm.aExitBttnClick(Sender: TObject);
begin
  application.terminate;
end;
procedure TaffordForm.aMortBttnClick(Sender: TObject);
begin
  form1.Show;
  affordForm.Hide;
end;
procedure TaffordForm.aTitleBttnClick(Sender: TObject);
begin
  titleform.show;
  affordForm.Hide;
end;
end.
