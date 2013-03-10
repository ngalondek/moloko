package dev.drsoran.moloko.app.contactslist;

interface IContactsListFragmentListener
{
   void onShowTasksOfContact( String fullname, String username );
   
   
   
   void onShowPhoneBookEntryOfContact( String lookUpKey );
}
