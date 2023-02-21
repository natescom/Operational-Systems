package model;
/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 04/10/2022
 * Ultima alteracao: 06/10/2022
 * Nome: Partition
 * Funcao: Define os atributos de uma Particao
 * **************************************************************
 */
public class Partition {
	public enum Type {
		P, H
	};

	private Type type;
	private int[] address;
	private int length;

	public Partition(Type type, int start, int end) {
		this.type = type;
		address = new int[2];
		address[0] = start;
		address[1] = end;
		length = end - start + 1;
	}

	public int[] getAddress() {
		return address;
	}

	public Type getType() {
			return type;
	}

	public int getLength() {
		return length;
	}

	public void changeType(){
		type = type == Type.H ? Type.P : Type.H;
	}

	public void setStart(int start){
		address[0] = start;
		length = address[1] - start + 1;
	}

	public void setEnd(int end){
		address[1]=end;
		length = end - address[0] + 1;
	}

	public int getStart(){
		return address[0];
	}

	public int getEnd(){
		return address[1];
	}

	public void setAddress(int start, int end){
		address[0] = start;
		address[1] = end;
		length = end - start + 1;
	}


}
