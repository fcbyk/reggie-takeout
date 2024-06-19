##  地址 address_book

这个表用于管理用户的地址信息，包括收货人信息、联系方式、地址详情等。

| 列名          | 数据类型     | 允许为空 | 默认值 | 注释              |
| ------------- | ------------ | -------- | ------ | ----------------- |
| id            | bigint(20)   | 否       |        | 主键              |
| user_id       | bigint(20)   | 否       |        | 用户id            |
| consignee     | varchar(50)  | 否       |        | 收货人            |
| sex           | tinyint(4)   | 否       |        | 性别 0 女 1 男    |
| phone         | varchar(11)  | 否       |        | 手机号            |
| province_code | varchar(12)  | 是       | NULL   | 省级区划编号      |
| province_name | varchar(32)  | 是       | NULL   | 省级名称          |
| city_code     | varchar(12)  | 是       | NULL   | 市级区划编号      |
| city_name     | varchar(32)  | 是       | NULL   | 市级名称          |
| district_code | varchar(12)  | 是       | NULL   | 区级区划编号      |
| district_name | varchar(32)  | 是       | NULL   | 区级名称          |
| detail        | varchar(200) | 是       | NULL   | 详细地址          |
| label         | varchar(100) | 是       | NULL   | 标签              |
| is_default    | tinyint(1)   | 否       | 0      | 默认 0 否 1 是    |
| create_time   | datetime     | 否       |        | 创建时间          |
| update_time   | datetime     | 否       |        | 更新时间          |
| create_user   | bigint(20)   | 否       |        | 创建人            |
| update_user   | bigint(20)   | 否       |        | 修改人            |
| is_deleted    | int(11)      | 否       | 0      | 是否删除 0 未删除 |

这个表设计用来存储用户的多个地址信息，支持设置默认地址，并记录创建和更新的时间和用户信息。

##  分类 category

这个表用于管理菜品及套餐的分类信息。

| 列名        | 数据类型    | 允许为空 | 默认值 | 注释                       |
| ----------- | ----------- | -------- | ------ | -------------------------- |
| id          | bigint(20)  | 否       |        | 主键                       |
| type        | int(11)     | 是       | NULL   | 类型 1 菜品分类 2 套餐分类 |
| name        | varchar(64) | 否       |        | 分类名称                   |
| sort        | int(11)     | 否       | 0      | 顺序                       |
| create_time | datetime    | 否       |        | 创建时间                   |
| update_time | datetime    | 否       |        | 更新时间                   |
| create_user | bigint(20)  | 否       |        | 创建人                     |
| update_user | bigint(20)  | 否       |        | 修改人                     |


这个表设计用来存储菜品及套餐的分类信息，包括分类的名称、类型（菜品分类或套餐分类）、顺序、创建时间、更新时间以及对应的创建人和修改人信息。

##  口味 dish_flavor

| 列名        | 类型         | 允许空值 | 默认值 | 注释         |
| ----------- | ------------ | -------- | ------ | ------------ |
| id          | bigint(20)   | 否       |        | 主键         |
| dish_id     | bigint(20)   | 否       |        | 菜品ID       |
| name        | varchar(64)  | 否       |        | 口味名称     |
| value       | varchar(500) | 是       | NULL   | 口味数据列表 |
| create_time | datetime     | 否       |        | 创建时间     |
| update_time | datetime     | 否       |        | 更新时间     |
| create_user | bigint(20)   | 否       |        | 创建人       |
| update_user | bigint(20)   | 否       |        | 修改人       |
| is_deleted  | int(11)      | 否       | 0      | 是否删除     |

这个表用于存储菜品的不同口味信息。每行数据对应特定菜品的一个口味，其中包含口味名称、口味数据列表（可选）、创建时间、更新时间、创建人、修改人和是否删除的信息。

## 员工 employee

这个表用于存储员工的基本信息。

| 列名        | 数据类型    | 允许为空 | 默认值 | 注释                |
| ----------- | ----------- | -------- | ------ | ------------------- |
| id          | bigint(20)  | 否       |        | 主键                |
| name        | varchar(32) | 否       |        | 姓名                |
| username    | varchar(32) | 否       |        | 用户名              |
| password    | varchar(64) | 否       |        | 密码                |
| phone       | varchar(11) | 否       |        | 手机号              |
| sex         | varchar(2)  | 否       |        | 性别                |
| id_number   | varchar(18) | 否       |        | 身份证号            |
| status      | int(11)     | 否       | 1      | 状态 0:禁用，1:正常 |
| create_time | datetime    | 否       |        | 创建时间            |
| update_time | datetime    | 否       |        | 更新时间            |
| create_user | bigint(20)  | 否       |        | 创建人              |
| update_user | bigint(20)  | 否       |        | 修改人              |


这个表设计用来存储员工的基本信息，包括姓名、用户名、密码、手机号、性别、身份证号等字段，同时记录了员工的状态（禁用或正常）、创建时间、更新时间以及创建人和修改人的信息。员工的用户名在表中是唯一的。

## 订单 orders

这个表用于存储订单的详细信息。

| 列名            | 数据类型      | 允许为空 | 默认值 | 注释                                                 |
| --------------- | ------------- | -------- | ------ | ---------------------------------------------------- |
| id              | bigint(20)    | 否       |        | 主键                                                 |
| number          | varchar(50)   | 是       | NULL   | 订单号                                               |
| status          | int(11)       | 否       | 1      | 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消 |
| user_id         | bigint(20)    | 否       |        | 下单用户                                             |
| address_book_id | bigint(20)    | 否       |        | 地址id                                               |
| order_time      | datetime      | 否       |        | 下单时间                                             |
| checkout_time   | datetime      | 否       |        | 结账时间                                             |
| pay_method      | int(11)       | 否       | 1      | 支付方式 1微信,2支付宝                               |
| amount          | decimal(10,2) | 否       |        | 实收金额                                             |
| remark          | varchar(100)  | 是       | NULL   | 备注                                                 |
| phone           | varchar(255)  | 是       | NULL   | 电话号码                                             |
| address         | varchar(255)  | 是       | NULL   | 地址                                                 |
| user_name       | varchar(255)  | 是       | NULL   | 用户名                                               |
| consignee       | varchar(255)  | 是       | NULL   | 收件人                                               |

这个表设计用来记录订单的各种信息，包括订单号、订单状态、下单用户、地址信息、订单时间、结账时间、支付方式、实收金额等。每个订单有一个唯一的主键 `id` 作为标识。

## 订单详细 order_detail

这个表用于记录订单中每个菜品或套餐的详细信息。

| 列名        | 数据类型      | 允许为空 | 默认值 | 注释   |
| ----------- | ------------- | -------- | ------ | ------ |
| id          | bigint(20)    | 否       |        | 主键   |
| name        | varchar(50)   | 是       | NULL   | 名字   |
| image       | varchar(100)  | 是       | NULL   | 图片   |
| order_id    | bigint(20)    | 否       |        | 订单id |
| dish_id     | bigint(20)    | 是       | NULL   | 菜品id |
| setmeal_id  | bigint(20)    | 是       | NULL   | 套餐id |
| dish_flavor | varchar(50)   | 是       | NULL   | 口味   |
| number      | int(11)       | 否       | 1      | 数量   |
| amount      | decimal(10,2) | 否       |        | 金额   |

这个表记录了每个订单的具体明细，包括菜品或套餐的名称、图片、口味、数量和金额等信息。每条明细有一个唯一的主键 `id` 标识。 `order_id` 则关联到订单表中的订单主键，以便与订单信息关联。

## 套餐 setmeal

这个表用于存储各种套餐的详细信息。

| 列名        | 数据类型      | 允许为空 | 默认值 | 注释       |
| ----------- | ------------- | -------- | ------ | ---------- |
| id          | bigint(20)    | 否       |        | 主键       |
| category_id | bigint(20)    | 否       |        | 菜品分类id |
| name        | varchar(64)   | 否       |        | 套餐名称   |
| price       | decimal(10,2) | 否       |        | 套餐价格   |
| status      | int(11)       | 是       | NULL   | 状态       |
| code        | varchar(32)   | 是       | NULL   | 编码       |
| description | varchar(512)  | 是       | NULL   | 描述信息   |
| image       | varchar(255)  | 是       | NULL   | 图片       |
| create_time | datetime      | 否       |        | 创建时间   |
| update_time | datetime      | 否       |        | 更新时间   |
| create_user | bigint(20)    | 否       |        | 创建人     |
| update_user | bigint(20)    | 否       |        | 修改人     |
| is_deleted  | int(11)       | 否       | 0      | 是否删除   |

这个表记录了各种套餐的详细信息，包括套餐的名称、价格、状态、描述、图片以及相关的创建和更新信息。每个套餐有一个唯一的主键 `id` 标识，且套餐名称 `name` 在表中是唯一的。 `category_id` 则关联到菜品分类表中的分类主键，用于标识套餐所属的菜品分类。

## 套餐详细 setmeal_dish

这个表用于记录套餐与菜品之间的关系和详细信息。

| 列名        | 数据类型      | 允许为空 | 默认值 | 注释                  |
| ----------- | ------------- | -------- | ------ | --------------------- |
| id          | bigint(20)    | 否       |        | 主键                  |
| setmeal_id  | varchar(32)   | 否       |        | 套餐id                |
| dish_id     | varchar(32)   | 否       |        | 菜品id                |
| name        | varchar(32)   | 是       | NULL   | 菜品名称 （冗余字段） |
| price       | decimal(10,2) | 是       | NULL   | 菜品原价（冗余字段）  |
| copies      | int(11)       | 否       |        | 份数                  |
| sort        | int(11)       | 否       | 0      | 排序                  |
| create_time | datetime      | 否       |        | 创建时间              |
| update_time | datetime      | 否       |        | 更新时间              |
| create_user | bigint(20)    | 否       |        | 创建人                |
| update_user | bigint(20)    | 否       |        | 修改人                |
| is_deleted  | int(11)       | 否       | 0      | 是否删除              |

这个表记录了每个套餐中包含的菜品信息，包括菜品在套餐中的份数、排序顺序，以及相关的创建和更新信息。每条记录通过 `id` 主键唯一标识，关联到对应的套餐和菜品。

## 购物车 shopping_cart

这个表用于存储用户的购物车信息，包括用户选择的菜品和套餐的具体内容。

| 列名        | 数据类型      | 允许为空 | 默认值 | 注释     |
| ----------- | ------------- | -------- | ------ | -------- |
| id          | bigint(20)    | 否       |        | 主键     |
| name        | varchar(50)   | 是       | NULL   | 名称     |
| image       | varchar(100)  | 是       | NULL   | 图片     |
| user_id     | bigint(20)    | 否       |        | 用户id   |
| dish_id     | bigint(20)    | 是       | NULL   | 菜品id   |
| setmeal_id  | bigint(20)    | 是       | NULL   | 套餐id   |
| dish_flavor | varchar(50)   | 是       | NULL   | 口味     |
| number      | int(11)       | 否       | 1      | 数量     |
| amount      | decimal(10,2) | 否       |        | 金额     |
| create_time | datetime      | 是       | NULL   | 创建时间 |

这个表记录了用户在购物车中添加的各种商品信息，包括单品和套餐，以及对应的数量和金额。每个记录通过 `id` 主键唯一标识，关联到特定用户的购物车内容。

## 用户 user

这个表用于存储用户的基本信息。

| 列名      | 数据类型     | 允许为空 | 默认值 | 注释                  |
| --------- | ------------ | -------- | ------ | --------------------- |
| id        | bigint(20)   | 否       |        | 主键                  |
| name      | varchar(50)  | 是       | NULL   | 姓名                  |
| phone     | varchar(100) | 否       |        | 手机号                |
| sex       | varchar(2)   | 是       | NULL   | 性别                  |
| id_number | varchar(18)  | 是       | NULL   | 身份证号              |
| avatar    | varchar(500) | 是       | NULL   | 头像                  |
| status    | int(11)      | 是       | 0      | 状态 (0:禁用，1:正常) |

这个表记录了用户的基本信息，包括姓名、手机号、性别、身份证号、头像以及用户状态。每个用户由唯一的 `id` 主键标识，用于关联其他表中的用户相关信息。