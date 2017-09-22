package br.ufpe.cin.if688.visitor;

import br.ufpe.cin.if688.ast.AssignStm;
import br.ufpe.cin.if688.ast.CompoundStm;
import br.ufpe.cin.if688.ast.EseqExp;
import br.ufpe.cin.if688.ast.Exp;
import br.ufpe.cin.if688.ast.ExpList;
import br.ufpe.cin.if688.ast.IdExp;
import br.ufpe.cin.if688.ast.LastExpList;
import br.ufpe.cin.if688.ast.NumExp;
import br.ufpe.cin.if688.ast.OpExp;
import br.ufpe.cin.if688.ast.PairExpList;
import br.ufpe.cin.if688.ast.PrintStm;
import br.ufpe.cin.if688.ast.Stm;
import br.ufpe.cin.if688.symboltable.IntAndTable;
import br.ufpe.cin.if688.symboltable.Table;

public class Interpreter implements IVisitor<Table> {

	// a=8;b=80;a=7;
	// a->7 ==> b->80 ==> a->8 ==> NIL
	private Table t;

	public Interpreter(Table t) {
		this.t = t;
	}

	@Override
	public Table visit(Stm s) {
		return s.accept(this);
	}

	@Override
	public Table visit(AssignStm s) {
		String id = s.getId();
		Exp exp = s.getExp();

		Table table = exp.accept(this);
		table.id = id;

		this.t = table;

		return this.t;
	}

	@Override
	public Table visit(CompoundStm s) {
		Stm stm1 = s.getStm1();
		Stm stm2 = s.getStm2();

		stm1.accept(this);
		stm2.accept(this);

		return this.t;
	}

	@Override
	public Table visit(PrintStm s) {
		ExpList expList = s.getExps();

		expList.accept(this);

		return this.t;
	}

	@Override
	public Table visit(Exp e) {
		return e.accept(this);
	}

	@Override
	public Table visit(EseqExp e) {
		IntAndTable intAndTable = new IntAndTableVisitor(this.t).visit(e);

		int result = intAndTable.result;

		return new Table("id", result, this.t);
	}

	@Override
	public Table visit(IdExp e) {
		IntAndTable intAndTable = new IntAndTableVisitor(this.t).visit(e);

		int result = intAndTable.result;

		return new Table("id", result, this.t);
	}

	@Override
	public Table visit(NumExp e) {
		IntAndTable intAndTable = new IntAndTableVisitor(this.t).visit(e);

		int result = intAndTable.result;

		return new Table("id", result, this.t);
	}

	@Override
	public Table visit(OpExp e) {
		IntAndTable intAndTable = new IntAndTableVisitor(this.t).visit(e);

		int result = intAndTable.result;

		return new Table("id", result, this.t);
	}

	@Override
	public Table visit(ExpList el) {
		return el.accept(this);
	}

	@Override
	public Table visit(PairExpList el) {
		System.out.print(el.getHead().accept(this).value + " ");

		el.getTail().accept(this);

		return this.t;
	}

	@Override
	public Table visit(LastExpList el) {
		System.out.println(el.getHead().accept(this).value);

		return this.t;
	}

}
