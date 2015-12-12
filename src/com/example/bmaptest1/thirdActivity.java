package com.example.bmaptest1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class thirdActivity extends Activity {
	
	private int i = 0; 
	private DbHelper3 db;
	public static SQLiteDatabase dbRead,dbWrite;
	private MyAdapter adapter;

	ExpandableListView mainListView = null;
	List<String> parent = null;
	Map<String,List<String>> map = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main3);
		
//		Intent intent = getIntent();
//		if(intent.getStringExtra("over") == "shut down")
//		{
//			finish();
//		}
		
		db = new DbHelper3(this,"dbo",null,1);
		dbRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();
	
		mainListView = (ExpandableListView)findViewById(R.id.main_expandablelistview);
		
        parent = new ArrayList<String>();
		
		parent.add("ѧУ����");
		parent.add("��ʦ����");
		parent.add("��������");
		parent.add("���˵绰");
		
		
		if(i <= 0 ){
			initDataBase();
			i++;
		}
		initData();
		
		adapter = new MyAdapter();
		
		mainListView.setAdapter(adapter);
		
		
		
		//���õ���¼��������һ��itemʱ������ݿ��ѯ����item����Ӧ�ĵ绰���룬Ȼ����ת������ú���Ľ���
		mainListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				
				    String str1 = (String)adapter.getGroup(groupPosition);
				    String str2 = (String)adapter.getChild(groupPosition, childPosition);
				    Cursor c = dbRead.query("user", new String[]{"_id","name","number"}, "category=?",new String[]{str1}, null, null, null);
				    while(c.moveToNext())
				    {
				    	if((c.getString(c.getColumnIndex("name")) + " : " + c.getString(c.getColumnIndex("number"))).equals(str2))
				    	{
				    		String itemNumber = c.getString(c.getColumnIndex("number"));
				    		Intent intent = new Intent(Intent.ACTION_DIAL);
							intent.setData(Uri.parse("tel:" + itemNumber));
							startActivity(intent);
							
				    	}
				    	
				    	
				    }
				
				return true;
			}
				});
		
		
	

		//���õ���¼���������һ��itemʱ����ʾ�Ƿ�ɾ������item�����ȷ����ɾ����item
		
mainListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
					final int arg2, long arg3) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(thirdActivity.this);
				dialog.setTitle("����").setMessage("��ȷ��Ҫɾ��������").setNegativeButton("ȡ��", null);
				dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
//						//Cursor c = adapter.getCursor();
//						Cursor c = dbRead.query("user", null, null, null, null, null, null);
//						c.moveToPosition(arg2);
//						
//						int itemId = c.getInt(c.getColumnIndex("_id"));
//						
//						dbWrite.delete("user", "_id = ?", new String[]{itemId+""});
//						
//						//initData();
//						refreshListView();
						
						int groupPos = (Integer)arg1.getTag(R.id.parent_textview);
						int childPos = (Integer)arg1.getTag(R.id.second_textview);
						
						if(childPos != -1)
						{
						    String str1 = (String)adapter.getGroup(groupPos);
						    String str2 = (String)adapter.getChild(groupPos, childPos);
						    System.out.println(str1);
						    System.out.println(str2);
						    Cursor c = dbRead.query("user", new String[]{"_id","name","number"}, "category=?",new String[]{str1}, null, null, null);
						    while(c.moveToNext())
						    {
						    	if((c.getString(c.getColumnIndex("name")) + " : " + c.getString(c.getColumnIndex("number"))).equals(str2))
						    	{
						    		int itemId = c.getInt(c.getColumnIndex("_id"));
									
									dbWrite.delete("user", "_id = ?", new String[]{itemId+""});
									//MainActivity.this.finish();
									
									//MainActivity.this.onCreate(null);
									Intent intent = new Intent(thirdActivity.this,MainActivity.class);
								
									startActivity(intent);
									getParent().finish();
									
									
						    	}
						    	
						    	
						    }
						    
						     
						     
						}
						
						
						
					}
				} ).show();

				
				
				return false;
			}
			
		});
		
	}
	
	//�����ݿ�����ʼ������
	public void initDataBase()
	{
//		ContentValues cv = new ContentValues();
//		cv.put("category", "ѧУ����");
//		cv.put("name", "����ѧԺ");
//		cv.put("number", "8056789");
//		dbWrite.insert("user", null, cv);
//		cv.clear();
//		
//		cv.put("category", "ѧУ����");
//		cv.put("name", "��еѧԺ");
//		cv.put("number", "8056469");
//		dbWrite.insert("user", null, cv);
//		cv.clear();
//		
//		cv.put("category", "��ʦ����");
//		cv.put("name", "���ƽ���");
//		cv.put("number", "8345789");
//		dbWrite.insert("user", null, cv);
//		cv.clear();
//		
//		cv.put("category", "��ʦ����");
//		cv.put("name", "��������");
//		cv.put("number", "7056789");
//		dbWrite.insert("user", null, cv);
//		cv.clear();
//		
//		cv.put("category", "��������");
//		cv.put("name", "���Ź�");
//		cv.put("number", "6156789");
//		dbWrite.insert("user", null, cv);
//		cv.clear();
//		
//		cv.put("category", "��������");
//		cv.put("name", "��Ƿ���");
//		cv.put("number", "8856780");
//		dbWrite.insert("user", null, cv);
//		cv.clear();
//		
        
	}
	
	private void initData()
	{
		
		
		map = new HashMap<String, List<String>>();
		
		//�����ݿ��в�ѯ�����Ϊ��ѧУ���š�����Ϣ��Ȼ��ӵ�list1��
		List<String> list1 = new ArrayList<String>();
		Cursor cs1 = dbRead.query("user", new String[]{"name","number"},"category = ?", new String[]{"ѧУ����"}, null, null, null);
		while(cs1.moveToNext())
			{
				String name = cs1.getString(cs1.getColumnIndex("name"));
				String number = cs1.getString(cs1.getColumnIndex("number"));
				list1.add(name + " : " + number);
			}
		map.put("ѧУ����", list1);
		
		//�����ݿ��в�ѯ�����Ϊ����ʦ���롱����Ϣ��Ȼ��ӵ�list2��
				List<String> list2 = new ArrayList<String>();
				Cursor cs2 = dbRead.query("user", new String[]{"name","number"},"category = ?", new String[]{"��ʦ����"}, null, null, null);
				while(cs2.moveToNext())
					{
						String name = cs2.getString(cs2.getColumnIndex("name"));
						String number = cs2.getString(cs2.getColumnIndex("number"));
						list2.add(name + " : " + number);
					}
				map.put("��ʦ����", list2);
		
		
				//�����ݿ��в�ѯ�����Ϊ���������ߡ�����Ϣ��Ȼ��ӵ�list1��
				List<String> list3 = new ArrayList<String>();
				Cursor cs3 = dbRead.query("user", new String[]{"name","number"},"category = ?", new String[]{"��������"}, null, null, null);
				while(cs3.moveToNext())
					{
						String name = cs3.getString(cs3.getColumnIndex("name"));
						String number = cs3.getString(cs3.getColumnIndex("number"));
						list3.add(name + " : " + number);
					}
				map.put("��������", list3);
				
				//�����ݿ��в�ѯ�����Ϊ�����˵绰������Ϣ��Ȼ��ӵ�list4��
				List<String> list4 = new ArrayList<String>();
				Cursor cs4 = dbRead.query("user", new String[]{"name","number"},"category = ?", new String[]{"���˵绰"}, null, null, null);
				while(cs4.moveToNext())
					{
						String name = cs4.getString(cs4.getColumnIndex("name"));
						String number = cs4.getString(cs4.getColumnIndex("number"));
						list4.add(name + " : " + number);
					}
				map.put("���˵绰", list4);
	}
	
	
	
	
	
	class MyAdapter extends BaseExpandableListAdapter
	{

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = parent.get(groupPosition);
			return map.get(key).get(childPosition);
			
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String key = thirdActivity.this.parent.get(groupPosition);
			String info = map.get(key).get(childPosition);
			if(convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater)thirdActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_children, null);
			}
			
			TextView tv = (TextView)convertView.findViewById(R.id.second_textview);
			convertView.setTag(R.id.second_textview, childPosition);
			convertView.setTag(R.id.parent_textview, groupPosition);
			tv.setText(info);
			return convertView;
			
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			String key = parent.get(groupPosition);
			int size = map.get(key).size();
			return size;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return parent.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return parent.size();
		}

		
		
		@Override
		public long getGroupId(int groupPosition) {
			return parent.size();
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if(convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater)thirdActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_parent, null);
			}
			
			TextView tv = (TextView)convertView.findViewById(R.id.parent_textview);
			convertView.setTag(R.id.second_textview, -1);
			convertView.setTag(R.id.parent_textview, groupPosition);
			tv.setText(thirdActivity.this.parent.get(groupPosition));
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		
	
		
		
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
