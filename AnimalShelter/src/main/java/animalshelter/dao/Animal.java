package animalshelter.dao;

public class Animal {

	private Long id;
	private String kind;
	private String name;

	public Animal(Long id, String kind, String name) {
		this.id = id;
		this.kind = kind;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}

}
