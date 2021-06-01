
pdfReader = PyPDF2.PdfFileReader(filename)
# creating a page object
pageObj = pdfReader.getPage(0)

# extracting text from page
inputs = pageObj.extractText()
# closing the pdf file object

labs = [list(label_index.keys())]

stopwords = ["a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought",
             "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "span"]


def clean_text(sentence):
    for word in stopwords:
        token = " " + word + " "
        sentence = str(sentence).replace(token, " ")
    sentence = re.sub('[^a-zA-Z]', ' ', sentence)
    sentence = re.sub(r"\s+[a-zA-Z]\s+", ' ', sentence)
    sentence = re.sub(r'\s+', ' ', sentence)
    return sentence


# print(str(inputs)+'\n')
clean_inputs = [clean_text(inputs)]
# print(str(clean_inputs)+'\n')
seq = tokenizer.texts_to_sequences(clean_inputs)
# print(str(seq)+'\n')
padded = pad_sequences(seq, maxlen=max_length,
                       padding=padding_type, truncating=trunc_type)
# print(str(padded)+'\n')

proba = [glove_model.predict(padded)[0, 1:]]

max = np.where(proba == np.amax(proba))
for [i] in max:
    idx = i
predlabel = labs[0][idx]
#predprob = proba[idx]*100
#print(list(map("{}: {:.2f}%".format,[predlabel],[predprob])))

if predlabel == 'software_developer':
    predlabel = 'Software Developer'
    predid = 1
elif predlabel == 'systems_administrator':
    predlabel = 'System Administrator'
    predid = 2
elif predlabel == 'project_manager':
    predlabel = 'Project Manager'
    predid = 3
elif predlabel == 'web_developer':
    predlabel = 'Web Developer'
    predid = 4
elif predlabel == 'database_administrator':
    predlabel = 'Database Administrator'
    predid = 5
elif predlabel == 'java_developer':
    predlabel = 'Java Developer'
    predid = 6
elif predlabel == 'python_developer':
    predlabel = 'Python Developer'
    predid = 7
elif predlabel == 'network_administrator':
    predlabel = 'Network Administrator'
    predid = 8
elif predlabel == 'security_analyst':
    predlabel = 'Security Analyst'
    predid = 9


conn = mysql.connect()
cur = conn.cursor(pymysql.cursors.DictCursor)

sql = "SELECT * FROM job JOIN company ON company.company_id = job.comp_id JOIN category ON category.category_id = job.cat_id where cat_id = %s"
data = (predid)
cur.execute(sql, data)
rows = cur.fetchall()
resp = jsonify(rows)
resp.status_code = 200
return resp
