package kr.gdu.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exchange {
	private int eno;
	private String code;
	private String name;
	private float sellamt;
	private float buyamt;
	private float priamt;
	private String edate;
}
