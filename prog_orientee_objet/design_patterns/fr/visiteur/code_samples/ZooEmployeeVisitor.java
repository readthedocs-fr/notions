class ZooEmployeeVisitor implements AnimalFeedingVisitor {
        
    @Override
    public void visit(Lion lion) {
        Food food = me.openBag().takeFoodForLions();
        me.feedCarefully(lion, food);
    }
        
    @Override
    public void visit(Whale whale) {
        int quantityToFeed = Zoo.maximalHungerOf(whale) - Zoo.foodAmountSoldForWhaleToday();
        if(quantityToFeed < 0) {
            ZooManager.report("The clients have fed the whale too much today :angryface:");
            return;
        }
        me.feed(whale, new WhaleFood(quantityToFeed));
    } 
        
    @Override
    public void visit(Duck duck) {
        System.out.println("Employees don't need to feed the ducks. Clients do so more than enough already!");
    }
}