/* 13 Feb 2022 15:44
Record messages from OscGroups, by user.
*/

OscHistory {
	classvar <users;

	initClass {
		users = IdentityDictionary()
	}

	*addEntry { | user, code, result, time |
		var theUser;
		theUser = users[user];
		theUser ?? {
			theUser = OscUser(user);
			users[user] = theUser;
		};
		theUser.addCodeEntry(code, result, time);
	}
}
