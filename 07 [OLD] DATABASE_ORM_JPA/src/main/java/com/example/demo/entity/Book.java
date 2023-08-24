package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    @Id
    @Column(length = 100)
    private long bookcode;
    @Column(length=255)
    private String bookname;
    @Column(length=255)
    private String publisher;
    @Column(length=255)
    private String isbn;

}

//----------------------------------
//@Column 어노테이션은 JPA에서 엔티티 클래스의 필드에 대한 세부 속성을 지정하는데 사용됩니다. 다음은 @Column 어노테이션의 주요 속성들에 대한 설명입니다:
//
//        name: 필드와 매핑되는 테이블의 컬럼 이름을 지정합니다. 기본적으로 필드의 이름과 동일하게 매핑됩니다.
//
//        nullable: 해당 컬럼이 null 값을 허용하는지 여부를 지정합니다. 기본값은 true로, null 값을 허용합니다. false로 설정하면 null 값을 허용하지 않습니다.
//
//        unique: 해당 컬럼에 중복된 값의 입력을 허용하는지 여부를 지정합니다. 기본값은 false로, 중복된 값을 허용합니다. true로 설정하면 중복된 값을 허용하지 않습니다.
//
//        length: 문자열 컬럼의 길이를 지정합니다. 주로 varchar 타입에 적용됩니다.
//
//        precision: 숫자 컬럼의 전체 자릿수를 지정합니다. 주로 decimal 타입에 적용됩니다.
//
//        scale: 숫자 컬럼의 소수 자릿수를 지정합니다. 주로 decimal 타입에 적용됩니다.
//
//        insertable: 해당 컬럼이 삽입 작업에 포함되는지 여부를 지정합니다. 기본값은 true로, 삽입 작업에 포함됩니다. false로 설정하면 삽입 작업에서 해당 컬럼은 제외됩니다.
//
//        updatable: 해당 컬럼이 업데이트 작업에 포함되는지 여부를 지정합니다. 기본값은 true로, 업데이트 작업에 포함됩니다. false로 설정하면 업데이트 작업에서 해당 컬럼은 제외됩니다.
//
//        columnDefinition: 컬럼의 데이터 타입 및 제약 조건을 직접 정의할 수 있습니다. 예를 들어, "VARCHAR(255) NOT NULL"과 같은 형식으로 컬럼을 정의할 수 있습니다.
//
//        insertable: 해당 컬럼이 삽입 작업에 포함되는지 여부를 지정합니다. 기본값은 true로, 삽입 작업에 포함됩니다. false로 설정하면 삽입 작업에서 해당 컬럼은 제외됩니다.
//
//        updatable: 해당 컬럼이 업데이트 작업에 포함되는지 여부를 지정합니다. 기본값은 true로, 업데이트 작업에 포함됩니다. false로 설정하면 업데이트 작업에서 해당 컬럼은 제외됩니다.

