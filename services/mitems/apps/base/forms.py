from django import forms


class ProceduresFileForm(forms.Form):
    file = forms.FileField(widget=forms.FileInput(attrs={'class': "file-input"}), label="")
