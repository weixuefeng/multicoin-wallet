# MultiCoin-wallet

## 钱包数据存储格式

|Field| Type | Desc |
|:-:|:-:|:-:|
| id | string | 钱包文件id，数据库的唯一标识 uuid|
| isMnemonic | boolean | 是否助记词钱包 |
| keystore | string | 钱包 keystore 内容 |
| type | string | default: WALLET_MAIN， 作为身份钱包信息，如果没有钱包时，创建的第一个钱包|
| timeStamp | long | 创建时间戳 |

> 1. 新用户: 打开钱包，设置密码，进入钱包，默认没有钱包信息
> 2. 进入钱包管理页面，选择对应币种创建钱包，第一次创建使用的 type 为 'WALLET_MAIN',是为身份钱包(主力钱包);
> 3. 比如第一次创建的 BTC, 第二次创建 ETH，那么还是在身份钱包的基础上衍生出 ETH 的钱包，不创建新的 storeKey;
> 4. 比如第一次创建的 BTC, 第二次创建 BTC, 那么重新生成新的 storeKey，type 为 CoinType.name

> 