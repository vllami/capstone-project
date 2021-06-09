What the cloud team has been working on which will be displayed on the Android User Interface

API Link : https://jobrec-ofah4hdyfa-et.a.run.app/joblist
Website  : https://job-finder-ofah4hdyfa-et.a.run.app/upload 

/joblist => method get, display all job list

/profile =>method get => display user profile (fullname, address, phone) 
	   method put => edit user profile ("fullname", "address", "phone")

/register => method POST => value json (fullname, username, email, pwd)

/login => method POST => value json (username, password)

/deletepdf => send get request then the pdf will be deleted based on user id

/logout => send get request 

/upload => send post request using form.body (in postman application)
