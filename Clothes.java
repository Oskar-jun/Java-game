public class Clothes extends Item{
    private boolean isWeared;
    private int placement;

    public Clothes(int rarity, String name, int placement){
        super(rarity, name);
        this.isWeared=false;
        this.placement=placement;
    }

    public void wear(){
        this.isWeared=!this.isWeared;
        if (this.isWeared){
            //Need getters in Item.
            System.out.println("Was equipped.");
        }else{
            System.out.println("Was taken off.");
        }
    }
}