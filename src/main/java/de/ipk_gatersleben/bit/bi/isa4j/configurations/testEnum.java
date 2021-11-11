package de.ipk_gatersleben.bit.bi.isa4j.configurations;

public interface testEnum {
	enum Color {
		red {
			@Override
			public void draw() {
			}
		},
		green {
			@Override
			public void draw() {
			}
		},
		blue {
			@Override
			public void draw() {
			}
		};

		public abstract void draw();
	}
}
