public class Subscription {
    private int installFee; // fixed Fee : 10$
    private int nbTV;
    private Subscriber subscriber;
    private SybscriptionCycle cycle;
    private String date;

    //Total fee
    private int totalFee;

    public Subscription(int nbTV, Subscriber subscriber, SybscriptionCycle cycle, String date) {
        this.nbTV = nbTV;
        this.subscriber = subscriber;
        this.cycle = cycle;
        this.date = date;

        this.installFee = nbTV * 10;
    }

    public int getNbTV() {
        return nbTV;
    }

    public void setNbTV(int nbTV) {
        this.nbTV = nbTV;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public SybscriptionCycle getCycle() {
        return cycle;
    }

    public void setCycle(SybscriptionCycle cycle) {
        this.cycle = cycle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalFee() {
        totalFee = installFee + 5;
        return totalFee;
    }


    @Override
    public String toString() {
        return "Subscription{" +
                "installFee=" + installFee +
                ", nbTV=" + nbTV +
                ", subscriber=" + subscriber +
                ", today=" + date+
                ", Cycle=" + cycle+
                '}';
    }
}
