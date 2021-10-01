# GPRAPP
GPRAPP ,which means generate password randomly application, is used to help you to generate and store a password randomly.

# Usage
you can first write the app name and the password length you wanted, and then just click the first button. If all your writting is OK, then a password will be generated and show in the bottom of the second button, if you think this password is not so good, you can reclick the first button to generate another on. Until you got a pretty good password, you need click the second button to copy the password to your clipboard, if everything is right, then you can goto the app to reset your password by it. The step is like this:
- write an appname
- write password length
- generate password
- copy password

When you forget the password, then you can goto the middle page and write the appname to see the all the password you have generate by this application, you can try one by one to the target application.

# Database
In this application, no database used now, but in the future may use sqlite database to store the passwords. It just use a file to store all the passwords, the data is a json, you can
find the data file in your mobile path `com.flyfish/data.json`. Now, all the passwords are plaintext, but we are thinking about to change to the ciphertext.

# Random generate password algorithm

Now, we just use the Random class and the timestamp to generate it, for more information, please view the method `com.flyfish.common.GlobalApp.genPassword`
