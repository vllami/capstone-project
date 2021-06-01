import PyPDF2
file = 'Jadwal_UP_2021_1.pdf'
pdfFileObj = open(
    'D://Kuliah//Bangkit//Capstone//job_finder//api_ML//uploads//'+file, 'rb')
# creating a pdf reader object
pdfReader = PyPDF2.PdfFileReader(file)
# creating a page object
pageObj = pdfReader.getPage(0)

# extracting text from page
inputs = pageObj.extractText()
# closing the pdf file object
print(inputs)
pdfFileObj.close()
