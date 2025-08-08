# Merciful Respawn MOD

## 概要
このMODは、MinecraftのDawn Craftでプレイヤーが死んだ際に、死亡地点の近くで復活し、プレイヤーが敵MOBを攻撃するまで敵MOBから攻撃されない機能を提供します。

## 機能
- **近距離復活**: プレイヤーが死んだ地点から設定可能な半径内で復活します
- **無敵期間**: 復活後、プレイヤーが敵MOBを攻撃するまで敵MOBから攻撃されません
- **設定可能**: 復活半径や無敵時間を設定ファイルで調整可能
- **Dawn Craft対応**: Dawn Craftとの互換性を考慮して設計されています

## 設定
MODの設定は `config/merciful-respawn-config.json` ファイルで変更できます。MODが初回読み込み時に自動的に生成されます：

```json
{
  "enableMod": true,
  "respawnRadius": 50,
  "gracePeriodTicks": 6000,
  "description": "Merciful Respawn MOD Configuration"
}
```

### 設定項目の詳細
- **enableMod**: MODの有効/無効を切り替えます（true/false）
- **respawnRadius**: 死亡地点からの復活可能範囲をブロック単位で指定（10-200推奨）
- **gracePeriodTicks**: 復活後の無敵時間をtick単位で指定（20tick = 1秒、1200-12000推奨）
- **description**: 設定ファイルの説明（変更不要）

### 設定ファイルの場所
- **生成場所**: `config/merciful-respawn-config.json`
- **自動生成**: MOD初回読み込み時に自動的に生成されます
- **手動編集**: ゲーム終了後にJSONファイルを直接編集可能

## 使用方法
1. MODをインストールしてMinecraftを起動
2. プレイヤーが死亡すると、死亡地点の近くで復活
3. 復活後、プレイヤーが敵MOBを攻撃するまで無敵状態
4. プレイヤーが敵MOBを攻撃すると、無敵状態が解除

### 動作の流れ
1. **死亡**: プレイヤーが死亡
2. **復活位置計算**: 死亡地点から設定された半径内で安全な位置を探索
3. **復活**: 計算された位置でプレイヤーを復活
4. **無敵期間開始**: 復活と同時に無敵期間が開始
5. **無敵期間終了**: プレイヤーが敵MOBを攻撃すると無敵期間が終了

## 技術仕様
- **Minecraft Version**: 1.18.2
- **Forge Version**: 40.2.0
- **Java Version**: 17
- **Gradle Version**: 7.5.1

## ビルド方法

### 前提条件
- Java 17以上
- Gradle 7.5.1以上

### ローカルビルド
```bash
# Windows
.\gradlew.bat build

# Linux/Mac
./gradlew build
```

### CI/CD
このプロジェクトはGitHub Actionsを使用してCI/CDを実行しています：

- **自動ビルド**: プッシュやプルリクエスト時に自動的にビルドが実行されます
- **テスト実行**: コードの品質を保つためにテストが実行されます
- **セキュリティ分析**: CodeQLを使用してセキュリティ脆弱性を検出します
- **依存関係更新**: Dependabotが定期的に依存関係を更新します

### ビルド成果物
- ビルドされたJARファイルはGitHub Actionsの成果物として保存されます
- リリース時に自動的にリリースアセットとしてアップロードされます

## インストール方法
1. ビルドしたJARファイルを `mods` フォルダに配置
2. Minecraft Forge 1.18.2をインストール
3. Minecraftを起動

### インストール手順の詳細
1. **Forgeのインストール**: Minecraft Forge 1.18.2-40.2.0をダウンロードしてインストール
2. **MODファイルの配置**: `merciful-respawn-1.0.0.jar`を`mods`フォルダにコピー
3. **Minecraftの起動**: ForgeプロファイルでMinecraftを起動
4. **設定の確認**: ゲーム内でMODが正常に読み込まれていることを確認

## トラブルシューティング

### よくある問題と解決方法

#### 1. MODが読み込まれない
- **原因**: Forgeのバージョンが合わない
- **解決方法**: Forge 1.18.2-40.2.0を使用していることを確認

#### 2. 復活が機能しない
- **原因**: 設定ファイルが正しく読み込まれていない
- **解決方法**: `config/mercifulrespawn-server.toml`の設定を確認

#### 3. 無敵期間が動作しない
- **原因**: 他のMODとの競合
- **解決方法**: 他のMODを一時的に無効にしてテスト

#### 4. ビルドエラー
- **原因**: Java 17がインストールされていない
- **解決方法**: Java 17をインストールしてPATHを設定

### ログの確認方法
MinecraftのログでMODの動作状況を確認できます：
- ログファイル: `logs/latest.log`
- MODの読み込み状況を確認
- エラーメッセージがあれば対処

## 開発

### 開発環境のセットアップ
1. リポジトリをクローン
2. Java 17をインストール
3. IDEでプロジェクトを開く（IntelliJ IDEA推奨）

### プロジェクト構造
```
merciful-respawn/
├── src/main/java/com/mercifulrespawn/
│   ├── MercifulRespawnMod.java      # メインMODクラス
│   ├── RespawnManager.java          # 復活管理
│   ├── MobAttackHandler.java        # MOB攻撃処理
│   ├── PlayerEventHandler.java      # プレイヤーイベント処理
│   ├── Config.java                  # 設定管理（ForgeConfigSpec）
│   └── JSONConfig.java             # JSON設定ファイル管理
├── src/main/resources/
│   ├── META-INF/mods.toml          # MODメタデータ
│   └── pack.mcmeta                 # リソースパックメタデータ
├── config-example.json              # 設定ファイルの例
└── build.gradle                    # ビルド設定
```

### テスト実行
```bash
./gradlew test
```

### コード品質
- コードは自動的に静的解析されます
- セキュリティ脆弱性は自動的に検出されます

## 更新履歴

### v1.0.0 (2024-08-08)
- 初回リリース
- 近距離復活機能
- 無敵期間機能
- 設定可能なパラメータ
- ResourcePackInfo警告の修正
- JSON設定ファイルの自動生成機能
- Gsonライブラリの統合

## 貢献
このプロジェクトへの貢献を歓迎します：

1. このリポジトリをフォーク
2. 機能ブランチを作成 (`git checkout -b feature/amazing-feature`)
3. 変更をコミット (`git commit -m 'Add amazing feature'`)
4. ブランチにプッシュ (`git push origin feature/amazing-feature`)
5. プルリクエストを作成

## ライセンス
MIT License

## サポート
問題が発生した場合は、GitHubのIssuesページで報告してください。
