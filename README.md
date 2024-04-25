## Client Base

一个我的世界1.8.9香草客户端基地，包含Optifine HD_U M6 pre2以及一些游戏优化

### 功能

新版本功能：

- 智能装配实例
- 自动注册几乎所有东西（Module，Command，Values）

基本功能：
- 模块系统
- 基本指令系统
- 一个示例HUD
- 15+事件示例
- 简易的数值系统
- 模块示例

### 更新内容

2024.04.26

- 修复Sprint在GrimAC被检测
- 自动注册Module和Command（通过注解）
```java
@Module(value = "Click Gui",category = Category.Render)
@Binding(Keyboard.KEY_RSHIFT)
public class ClickGui implements Access.InstanceAccess {
    
}
```

- 自动装配Bean到方法，构造，字段（Autowired）

Powered by [Lite Invoke](https://github.com/cubk1/LiteInvoke)

```java
    @Enable
    // 此处的clickGuiScreen参数为自动装配
    public void onEnable(ClickGuiScreen clickGuiScreen){
        setEnable(this,false);
        mc.displayGuiScreen(clickGuiScreen);
    }
```

```java
    private final ModuleManager moduleManager;

    // 注册时这里会自动装配Module Manager实例
    public Sprint(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }
```

```java
    // 运行时这里会自动装配Sprint实例
    @LiteInvoke.Autowired
    private Sprint sprint;

    // 运行时这里会自动装配Module Manager实例
    @LiteInvoke.Autowired
    private ModuleManager moduleManager;
```

```java
    @EventTarget
    // 对于Event方法同样适用，运行时这里会自动装配CommandManager
    public void onUpdate(TickEvent event, CommandManager commandManager) {
        setSuffix(moduleManager.getModules().size() + " Modules and " + commandManager.getCommands().size() + " Commands!", this);

        mc.gameSettings.keyBindSprint.pressed = true;
    }
```

![i](screenshot/1.png)

- 多个Handler的Command

```java
@Command(value = "bind",usage = "bind <module> <key>")
public class BindCommand implements Access.InstanceAccess {

	@Command.Handler // 第二个Handler，所有Handler都支持自动装配实例
	public void secondHandler(String[] args, Sprint sprint) {
		ChatUtil.info("Received " + args.length + " parameter(s)!");
		ChatUtil.info("Sprint class is " + sprint.getClass().getName());
	}

	@Command.Handler // 第一个Handler
	public void run(String[] args) {
		if (args.length == 3) {
			Class<?> module = access.getModuleManager().getModuleClass(args[1]);
			if (module != null) {
				access.getModuleManager().setKey(module, Keyboard.getKeyIndex(args[2].toUpperCase()));
				ChatUtil.info(access.getModuleManager().getName(module) + " has been bound to " + args[2] + ".");
			} else {
				ChatUtil.info(args[1] + " not found.");
			}
		}else {
			ChatUtil.info(usage(this));
		}
	}
}

```
![i](screenshot/2.png)
### 致谢

- [Event API](https://bitbucket.org/DarkMagician6/eventapi)