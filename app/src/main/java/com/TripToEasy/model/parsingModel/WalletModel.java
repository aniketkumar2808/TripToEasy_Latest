package com.TripToEasy.model.parsingModel;

public class WalletModel {

    private String app_reference;
        private String amount;
        private String credit;
        private String debit;
        private String opening_balance;
        private String closing_balance;
        private String date;
        private String status;
        private Object promo_applied;


        public WalletModel(String app_reference,String amount,String credit,String debit,
                           String opening_balance,String closing_balance,String date,String status)
        {
            this.app_reference=app_reference;
            this.amount=amount;
            this.credit=credit;
            this.debit=debit;
            this.opening_balance=opening_balance;
            this.closing_balance=closing_balance;
            this.date=date;
            this.status=status;
        }



        public String getApp_reference() {
            return app_reference;
        }

        public void setApp_reference(String app_reference) {
            this.app_reference = app_reference;
        }


        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getDebit() {
            return debit;
        }

        public void setDebit(String debit) {
            this.debit = debit;
        }

        public String getOpening_balance() {
            return opening_balance;
        }

        public void setOpening_balance(String opening_balance) {
            this.opening_balance = opening_balance;
        }

        public String getClosing_balance() {
            return closing_balance;
        }

        public void setClosing_balance(String closing_balance) {
            this.closing_balance = closing_balance;
        }
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getPromo_applied() {
            return promo_applied;
        }

        public void setPromo_applied(Object promo_applied) {
            this.promo_applied = promo_applied;
        }

}
