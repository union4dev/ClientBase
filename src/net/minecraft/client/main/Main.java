package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Main
{
    public static void main(String[] args)
    {
        System.setProperty("java.net.preferIPv4Stack", "true");
        final OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        final OptionSpec<String> server = optionParser.accepts("server").withRequiredArg();
        final OptionSpec<Integer> port = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
        final OptionSpec<File> gameDir = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        final OptionSpec<File> assetsDir = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        final OptionSpec<File> resourcePackDir = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        final OptionSpec<String> proxyHost = optionParser.accepts("proxyHost").withRequiredArg();
        final OptionSpec<Integer> proxyPort = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        final OptionSpec<String> proxyUser = optionParser.accepts("proxyUser").withRequiredArg();
        final OptionSpec<String> proxyPass = optionParser.accepts("proxyPass").withRequiredArg();
        final OptionSpec<String> username = optionParser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L);
        final OptionSpec<String> uuid = optionParser.accepts("uuid").withRequiredArg();
        final OptionSpec<String> accessToken = optionParser.accepts("accessToken").withRequiredArg().required();
        final OptionSpec<String> version = optionParser.accepts("version").withRequiredArg().required();
        final OptionSpec<Integer> width = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        final OptionSpec<Integer> height = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        final OptionSpec<String> userProperties = optionParser.accepts("userProperties").withRequiredArg().defaultsTo("{}");
        final OptionSpec<String> profileProperties = optionParser.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
        final OptionSpec<String> assetIndex = optionParser.accepts("assetIndex").withRequiredArg();
        final OptionSpec<String> userType = optionParser.accepts("userType").withRequiredArg().defaultsTo("legacy");
        final OptionSpec<String> nonOptions = optionParser.nonOptions();
        final OptionSet parse = optionParser.parse(args);
        final List<String> list = parse.valuesOf(nonOptions);

        if (!list.isEmpty())
        {
            System.out.println("Completely ignored arguments: " + list);
        }

        final String proxyHostStr = parse.valueOf(proxyHost);
        Proxy proxy = Proxy.NO_PROXY;

        if (proxyHostStr != null)
        {
            try
            {
                proxy = new Proxy(Type.SOCKS, new InetSocketAddress(proxyHostStr, parse.valueOf(proxyPort)));
            }
            catch (Exception ignored)
            {
            }
        }

        final String s1 = parse.valueOf(proxyUser);
        final String s2 = parse.valueOf(proxyPass);

        if (!proxy.equals(Proxy.NO_PROXY) && isNullOrEmpty(s1) && isNullOrEmpty(s2))
        {
            Authenticator.setDefault(new Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(s1, s2.toCharArray());
                }
            });
        }

        int i = parse.valueOf(width);
        int j = parse.valueOf(height);
        boolean flag = parse.has("fullscreen");
        boolean flag1 = parse.has("checkGlErrors");
        boolean flag2 = parse.has("demo");
        String s3 = parse.valueOf(version);
        Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
        PropertyMap propertymap = gson.fromJson(parse.valueOf(userProperties), PropertyMap.class);
        PropertyMap propertymap1 = gson.fromJson(parse.valueOf(profileProperties), PropertyMap.class);
        File file1 = parse.valueOf(gameDir);
        File file2 = parse.has(assetsDir) ? parse.valueOf(assetsDir) : new File(file1, "assets/");
        File file3 = parse.has(resourcePackDir) ? parse.valueOf(resourcePackDir) : new File(file1, "resourcepacks/");
        String s4 = parse.has(uuid) ? uuid.value(parse) : username.value(parse);
        String s5 = parse.has(assetIndex) ? assetIndex.value(parse) : null;
        String s6 = parse.valueOf(server);
        Integer integer = parse.valueOf(port);
        Session session = new Session(username.value(parse), s4, accessToken.value(parse), userType.value(parse));
        GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1, proxy), new GameConfiguration.DisplayInformation(i, j, flag, flag1), new GameConfiguration.FolderInformation(file1, file3, file2, s5), new GameConfiguration.GameInformation(flag2, s3), new GameConfiguration.ServerInformation(s6, integer));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
        {
            public void run()
            {
                Minecraft.stopIntegratedServer();
            }
        });
        Thread.currentThread().setName("Client thread");
        (new Minecraft(gameconfiguration)).run();
    }

    private static boolean isNullOrEmpty(String str)
    {
        return str != null && !str.isEmpty();
    }
}
