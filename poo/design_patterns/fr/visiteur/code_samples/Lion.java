class Lion implements Animal {
         
    @Override
    public void accept(ZooFeedingVisitor visitor) {
        visitor.visit(this);
    }
}