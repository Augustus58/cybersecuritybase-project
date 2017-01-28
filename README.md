# A10-UnvaliAdated Redirects and Forwards

## How the flaw can be identified?

###Owasp Zap
1. Start Owasp Zap
2. Start the application
3. Configure your browser to use the Zap proxy
4. Go to address localhost:8080/form in the browser
5. Send a signup with the ui
6. Go to the Zap and look for messages under site http://localhost:8080
7. Choose the most recent message named GET:formSave(address,name,redirect)
8. Open active scan dialog for the message
9. Start the scan with default options
10. Look for Alerts tab
11. There should be "External Redirect" alerts
12. Look for the related POST message (Request)
13. Identify the redirect parameter with replaced address
14. Copy the url of the GET (e.g.http://localhost:8080/formSave?name=test&address=test&redirect=http%3A%2F%2F4728989909065774721.owasp.org) and replace the redirect address with e.g. https://www.owasp.org/index.php/Top_10_2013-A10-Unvalidated_Redirects_and_Forwards
15. Copy the modified address to your browser and test it out.

## How the flaw can be fixed?
Do not use redirect-parameters on the first hand. And if it's really needed to use redirect-parameters, then the parameters should be whitelisted. Whitelisted so that in each redirect only explicitly allowed addresses are allowed.

In code level redirect parameter should be removed from form-view. Also in signupcontrollers submitForm-method redirect parameter is not needed and should be removed. Or whitelisted.