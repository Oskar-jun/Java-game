public class Ship_Boosters extends Item{
    private boolean isWeared;

    public Ship_Boosters(String name){
       super(name);
        this.isWeared=false;
    }

    // If smth weared automatically, change other items to non equiped

    public void wear(){
        this.isWeared=!this.isWeared;
        if (this.isWeared){
            System.out.println("Was equipped.");
        }else{
            System.out.println("Was taken off.");
        }
    }
}