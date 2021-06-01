import PyPDF2
file = '//Jadwal_UP_2021_1.pdf'
pdfFileObj = open(file, 'rb')
# creating a pdf reader object
pdfReader = PyPDF2.PdfFileReader(pdfFileObj)
# creating a page object
pageObj = pdfReader.getPage(0)

# extracting text from page
inputs = pageObj.extractText()
# closing the pdf file object
print(inputs)
pdfFileObj.close()
