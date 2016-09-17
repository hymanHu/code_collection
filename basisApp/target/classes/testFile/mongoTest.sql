/*Drop Collections if exist */
db.menu.drop();

/* Initial Menu */
db.menu.insert({'name':'Roles','age':'12', 'url':'/account/getRoleList.do', 'group':'Account Managment','sequence':'01-01'});
db.menu.insert({'name':'Users','age':'22', 'url':'/account/getAccountList.do', 'group':'Account Managment','sequence':'01-02'});
db.menu.insert({'name':'Message Sending', 'age':'33','url':'/message/showSendMessage.do', 'group':'Messaging','sequence':'02-01'});
db.menu.insert({'name':'Message List', 'age':'15','url':'/message/messageList.do', 'group':'Messaging','sequence':'02-02'});
