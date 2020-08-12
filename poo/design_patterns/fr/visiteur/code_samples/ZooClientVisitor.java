class ZooClientVisitor implements AnimalFeedingVisitor {
         
    @Override
    public void visit(Lion lion) {
        System.out.println("Unable to feed the lions. They're too dangerous to be fed by clients.");
    }
        
    @Override
    public void visit(Whale whale) {
        // Pseudo code, il s'agit d'une implémentation qui serait possible
        Food food = Market.buyFoodForWhales();
        Zoo.throwFoodInCage(whale, food);
    }
    
    @Override
    public void visit(Duck duck) {
        // Pseudo code, il s'agit d'une implémentation qui serait possible
        if(!me.hasBreadInPockets()) {
            System.out.println("You have no bread to feed the ducks :(");
            return;
        }
           
        Bread bread = me.takeOutBreadFromPocket();
        bread.tornAppart();
        me.throwBread(bread);
    }
}