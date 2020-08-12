class DuckMom extends Duck {

    private List<Duck> children;
    
    public DuckCage(List<Duck> children) {
        this.children = children;
    }
    
    @Override
    public void accept(AnimalFeedingVisitor visitor) {
        visitor.visit(this);
        for(Duck child : children) {
            visitor.visit(child);
        }
    }
}
