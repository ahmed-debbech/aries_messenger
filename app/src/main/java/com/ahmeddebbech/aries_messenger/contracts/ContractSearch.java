package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractSearch {
    interface  View{
        void showResults();
    }
    interface Presenter{
        void fillSearchResults(String text);
    }
}
