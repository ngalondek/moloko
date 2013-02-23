package dev.drsoran.moloko.app.contactslist;

public interface IContactsListFragmentListener
{
   void onShowTasksOfContact( String fullname, String username );
   
   
   
   void onShowPhoneBookEntryOfContact( String lookUpKey );
}
