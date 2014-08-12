unit titlePage;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, Vcl.StdCtrls, Vcl.ExtCtrls,project2,affordPage,
  Vcl.Imaging.jpeg;

type
  TtitleForm = class(TForm)
    titleImg: TImage;
    tAffordBttn: TButton;
    tMortBttn: TButton;
    tExitBttn: TButton;
    Image2: TImage;
    procedure tExitBttnClick(Sender: TObject);
    procedure tMortBttnClick(Sender: TObject);
    procedure tAffordBttnClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  titleForm: TtitleForm;

implementation

{$R *.dfm}

procedure TtitleForm.tExitBttnClick(Sender: TObject);
begin
application.Terminate;
end;
procedure TtitleForm.tAffordBttnClick(Sender: TObject);
begin
  affordform.show;
  titleform.Hide;
end;
procedure TtitleForm.tMortBttnClick(Sender: TObject);
begin
  form1.show;
  titleform.hide;
end;

end.
