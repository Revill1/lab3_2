package lab3_2;

import static org.powermock.api.mockito.PowerMockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.Configuration;
import edu.iis.mto.staticmock.ConfigurationLoader;
import edu.iis.mto.staticmock.IncomingInfo;
import edu.iis.mto.staticmock.IncomingNews;
import edu.iis.mto.staticmock.NewsLoader;
import edu.iis.mto.staticmock.NewsReaderFactory;
import edu.iis.mto.staticmock.PublishableNews;
import edu.iis.mto.staticmock.SubsciptionType;
import edu.iis.mto.staticmock.reader.NewsReader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class , NewsReaderFactory.class})
public class TestLoadNews {

	private ConfigurationLoader confLoader;
	private NewsReaderFactory factory;
	private NewsReader reader;
	private NewsLoader loader;
	
	
	@Before
	public void start()
	{
		confLoader = mock(ConfigurationLoader.class);
		when(confLoader.loadConfiguration()).thenReturn(new Configuration());
		mockStatic(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(confLoader);

		reader = mock(NewsReader.class);
		when(reader.read()).thenReturn(new IncomingNews());

		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader(Mockito.anyString())).thenReturn(reader);

		loader = new NewsLoader();
	}
	
	@Test
	public void newsTest()
	{
		IncomingNews incomingNews = new IncomingNews();
		incomingNews.add(new IncomingInfo("type a",SubsciptionType.A));
		incomingNews.add(new IncomingInfo("type b",SubsciptionType.B));
		incomingNews.add(new IncomingInfo("type c",SubsciptionType.NONE));
		incomingNews.add(new IncomingInfo("type c",SubsciptionType.NONE));
		
		when(reader.read()).thenReturn(incomingNews);
		
		PublishableNews news = loader.loadNews();
		Assert.assertEquals(news.getPublicContent().size(), 2);
	}
	
	@Test
	public void subscribersNewsTest()
	{
		IncomingNews incomingNews = new IncomingNews();
		incomingNews.add(new IncomingInfo("type a",SubsciptionType.A));
		incomingNews.add(new IncomingInfo("type b",SubsciptionType.C));
		incomingNews.add(new IncomingInfo("type c",SubsciptionType.B));
		
		when(reader.read()).thenReturn(incomingNews);
		
		PublishableNews news = loader.loadNews();
		Assert.assertEquals(news.getSubscribentContent().size(), 3);
	}
	
	@Test
	public void behaviourTest()
	{
		loader.loadNews();
		Mockito.verify(reader, Mockito.times(1)).read();
	}
	
}
