package com.McGillShopify.DMA.math140;

public class DoneWithProblemType extends Exception{
	public String problem;
	public DoneWithProblemType(){
		super();
	}
	public DoneWithProblemType(String problemType){
		super("Done with "+problemType+" problems");
		problem=problemType;
	}
}
