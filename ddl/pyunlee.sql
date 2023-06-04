CREATE TABLE products
(
	productId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '상품ID',
    productName VARCHAR(50) NOT NULL COMMENT '상품이름',
    brandName VARCHAR(50) NOT NULL COMMENT '브랜드이름',
    price BIGINT(20) UNSIGNED NOT NULL COMMENT '가격',
    productCategoryType VARCHAR(10) NOT NULL COMMENT '상품카테고리타입',
    barcode VARCHAR(13) NOT NULL COMMENT '바코드',
    imageUrl TEXT NOT NULL COMMENT '이미지링크',
    valid TINYINT(1) NOT NULL COMMENT 'valid',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품목록';

create index products_idx01 on products (createdAt);
create index products_idx02 on products (updatedAt);
create index producproduct_retailer_mappingsts_idx03 on products (productName,brandName);

CREATE TABLE product_retailer_mappings
(
	productRetailerMappingId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'productRetailerMappingId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    retailerType VARCHAR(20) NOT NULL COMMENT '편의점종류',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='상품 편의점 mapping';

create index product_retailer_mappings_idx01 on product_retailer_mappings (createdAt);
create index product_retailer_mappings_idx02 on product_retailer_mappings (updatedAt);
create index product_retailer_mappings_idx03 on product_retailer_mappings (productId);
create index product_retailer_mappings_idx04 on product_retailer_mappings (retailerType);

CREATE TABLE pb_product_mappings
(
	pbProductMappingId BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'pbProductMappingId',
    productId BIGINT(20) UNSIGNED COMMENT '상품ID',
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시'
) CHARSET = utf8 COMMENT='pb 상품 mapping';

create index pb_product_mappings_idx01 on pb_product_mappings (createdAt);
create index pb_product_mappings_idx02 on pb_product_mappings (updatedAt);
create index pb_product_mappings_idx03 on pb_product_mappings (productId);