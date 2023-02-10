package com.globits.wl.dto;

import com.globits.wl.domain.HusbandryType;

public class HusbandryTypeDto {

		private Long id;
		private String name;
		private String code;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public HusbandryTypeDto() {
			super();
		}

		public HusbandryTypeDto(HusbandryType husbandryType) {
			super();
			this.id = husbandryType.getId();
			this.name = husbandryType.getName();
			this.code = husbandryType.getCode();
		}
		
		
		

}